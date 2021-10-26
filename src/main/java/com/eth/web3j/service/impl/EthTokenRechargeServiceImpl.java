/**
 * 版权声明： 版权所有 违者必究 2020
*/
package com.eth.web3j.service.impl;

import com.alibaba.fastjson.JSON;
import com.eth.web3j.common.CommonRedisKey;
import com.eth.web3j.domain.EthTokenCoinConfig;
import com.eth.web3j.domain.EthTokenCollect;
import com.eth.web3j.domain.EthTokenRecharge;
import com.eth.web3j.domain.EthUserWallet;
import com.eth.web3j.domain.vo.TokenInputDto;
import com.eth.web3j.mapper.EthTokenCoinConfigMapper;
import com.eth.web3j.mapper.EthTokenCollectMapper;
import com.eth.web3j.service.EthBlockService;
import com.eth.web3j.service.EthTokenRechargeService;
import com.eth.web3j.service.EthUserWalletService;
import com.eth.web3j.util.EthereumUtils;
import com.eth.web3j.util.Help;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eth.web3j.mapper.EthTokenRechargeMapper;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.utils.Convert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;


/**
 * <p>业务接口实现类</p>
 * <p>用户充值,当前用户充值成功之后添加数据到这个表,充值一般无手续费.当status为0和confirm=1的时候表示充值成功</p>
 *
 * @author gaog
 * @since 2021-07-16 05:36:07
 */
@Service
@Transactional
@Slf4j
public class EthTokenRechargeServiceImpl extends ServiceImpl<EthTokenRechargeMapper, EthTokenRecharge> implements EthTokenRechargeService {

    @Resource
    private EthTokenCoinConfigMapper configMapper;

    @Resource
    private EthBlockService ethBlockService;

    @Resource
    private EthUserWalletService ethUserWalletService;


    @Resource
    private EthTokenRechargeMapper ethTokenRechargeMapper;

    @Resource
    private EthTokenCollectMapper tokenCollectMapper;


    @Autowired
    private StringRedisTemplate redisTemplate;


    @Value("${eth.main.address}")
    private String MAIN_ADDRESS;


    /**
     * 扫快 确定交易
     */
    @Override
    public void rechargeJob() {

        List<EthTokenCoinConfig> list = configMapper.getEnableAllByType("eth");

        Map<String, EthTokenCoinConfig> cmap = list.stream().collect(Collectors.toMap(EthTokenCoinConfig::getCoin, a -> a, (k1, k2) -> k1));

        EthTokenCoinConfig ethCoinConfig = cmap.get("ETH");

        Map<String, EthTokenCoinConfig> tokenConfigMap = list.stream().filter(configEntity -> Help.isNotNull(configEntity.getContract())).collect(Collectors.toMap(EthTokenCoinConfig::getContract, a -> a, (k1, k2) -> k1));

        //获取当前区块高度
        BigInteger currentBlock = ethBlockService.queryBlockLast();//获取最新区块高度

        //分开处理
        BigInteger oldBlock = ethCoinConfig.getBlockNo(); //上次已检索的区块高度
        log.info(log.getName() + ".collectionRechargeJob currentBlock:{}, oldBlock:{}", currentBlock, oldBlock);

        while (currentBlock.compareTo(oldBlock) > 0) {
            oldBlock = oldBlock.add(BigInteger.ONE);
            List<EthBlock.TransactionResult> transactionResultList = ethBlockService.getTransactionsByBlockNo(oldBlock);

            //获取接收地址
            Map<String, TokenInputDto> map = new HashMap<>();
            final Set<String> receivers = new HashSet<>();

            //把所有集合交易装起来
            transactionResultList.stream().forEach(transactionResult -> {
                EthBlock.TransactionObject transaction = (EthBlock.TransactionObject) transactionResult;
                String input = transaction.getInput();
                // 判断是否属于HAI合约
                if (tokenConfigMap.containsKey(transaction.getTo())) {
                    EthTokenCoinConfig tokenConfigEntity = tokenConfigMap.get(transaction.getTo());
                    TokenInputDto tokenInputDto = this.parseInput(transaction.getInput(), tokenConfigEntity.getRound());
                    if (null != tokenInputDto) {
                        tokenInputDto.setIsContract(true);
                        tokenInputDto.setContract(tokenConfigEntity.getContract());
                        tokenInputDto.setBlockNo(transaction.getBlockNumber());
                        tokenInputDto.setSender(transaction.getFrom());
                        map.put(transaction.getHash(), tokenInputDto);
                        receivers.add(tokenInputDto.getReceiver());
                    }
                } else if ("0x".equals(input)) {
                    map.put(transaction.getHash(), TokenInputDto.builder()
                            .sender(transaction.getFrom())
                            .receiver(transaction.getTo())
                            .amount(Convert.fromWei(new BigDecimal(transaction.getValue()), Convert.Unit.ETHER))
                            .blockNo(transaction.getBlockNumber())
                            .isContract(false)
                            .build());
                }
            });

            Set<String> existLowerAddress = new HashSet<>();
            if (receivers.size() != 0) {
                existLowerAddress = ethUserWalletService.existLowerAddress(receivers);
            }

            if (!existLowerAddress.isEmpty()) {

                Set<Map.Entry<String, TokenInputDto>> entrySet = map.entrySet();
                Iterator<Map.Entry<String, TokenInputDto>> its = entrySet.iterator();
                while (its.hasNext()) {
                    Map.Entry<String, TokenInputDto> entry = its.next();
                    TokenInputDto tokenInputDto = entry.getValue();
                    if ( !MAIN_ADDRESS.equals(tokenInputDto.getReceiver())//主钱包不处理
                            && existLowerAddress.contains(tokenInputDto.getReceiver())) {
                        try {
                            log.info(log.getName() + ".tokenInputDto:{}", JSON.toJSON(tokenInputDto));
                            EthTokenCoinConfig coinConfig = ethCoinConfig;
                            if (tokenInputDto.getIsContract()) {
                                coinConfig = tokenConfigMap.get(tokenInputDto.getContract());
                            }
                            EthUserWallet ecsUserWallet = ethUserWalletService.getByAddress(tokenInputDto.getReceiver());
                            if (Help.isNotNull(ecsUserWallet)) {
                                String key = CommonRedisKey.ETH_RECHARGE_CHECK_KEY + tokenInputDto.getBlockNo();
                                String hkey = entry.getKey();
                                if (redisTemplate.opsForHash().hasKey(key, hkey)) {
                                    log.error(log.getName() + ".transEthToken.address={}, key={}, hkey={}, 该笔充值已处理, 不能重复入账!", tokenInputDto.getReceiver(), key, hkey);
                                    continue;
                                }

                                // 插入充币数据--相当于是一个历史记录
                                EthTokenRecharge ecsUserRecharge = EthTokenRecharge.builder()
                                        .uid(ecsUserWallet.getUid())
                                        .txid(entry.getKey())
                                        .num(tokenInputDto.getAmount())
                                        .mum(tokenInputDto.getAmount())
                                        .blockNumber(tokenInputDto.getBlockNo())
                                        .coinType(coinConfig.getCoinType())
                                        .coinName(coinConfig.getCoin())
                                        .address(tokenInputDto.getReceiver())
                                        .coinId(coinConfig.getCoinId())
                                        .status(-1)//状态：0-待入帐；1-充值成功，2到账失败，3到账成功；
                                        .created(new Date())
                                        .build();
                                ethTokenRechargeMapper.insert(ecsUserRecharge);

                                // 插入归集数据 ----到账后需要发送到主钱包
                                EthTokenCollect coinCollectEntity = EthTokenCollect.builder()
                                        .coinId(coinConfig.getCoinId())
                                        .coinType(coinConfig.getCoinType())
                                        .coinName(coinConfig.getCoin())
                                        .contract(coinConfig.getContract())
                                        .receiver(tokenInputDto.getReceiver())
                                        .token(coinConfig.getToken())
                                        .num(tokenInputDto.getAmount())
                                        .remark("")
                                        .createTime(new Date())
                                        .updateTime(new Date())
                                        .build();
                                tokenCollectMapper.insert(coinCollectEntity);
                                redisTemplate.opsForHash().put(key, hkey, JSON.toJSONString(tokenInputDto));
                            }

                        } catch (DuplicateKeyException e) {
                            e.printStackTrace();
                            log.error(log.getName() + ".collectionRechargeJob.DuplicateKeyException.error", e.getMessage());
                        } catch (Exception e) {
                            e.printStackTrace();
                            log.error(log.getName() + ".collectionRechargeJob.Exception.error", e.getMessage());
                        }
                    }
                }
            }
            configMapper.updateActionSeqById(ethCoinConfig.getId(), oldBlock);
        }

    }


    private TokenInputDto parseInput(String data, int round) {
        if (data.length() == 138) {
            String toAdress = "0x" + data.substring(34, 74);
            String amountHex = "0x" + data.substring(74, 138).replaceFirst("^0*", "");
            return TokenInputDto.builder().receiver(toAdress).amount(EthereumUtils.hexToBigDecimal(amountHex, round)).build();
        }
        return null;
    }
}