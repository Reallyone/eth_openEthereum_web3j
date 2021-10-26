/**
 * 版权声明： 版权所有 违者必究 2020
 */
package com.eth.web3j.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eth.web3j.core.Web3JAdminClient;
import com.eth.web3j.core.Web3JClient;
import com.eth.web3j.domain.EthTokenCoinConfig;
import com.eth.web3j.domain.EthTokenCollect;
import com.eth.web3j.domain.EthUserWallet;
import com.eth.web3j.domain.vo.TokenTransEntranceDto;
import com.eth.web3j.service.EthTokenCoinConfigService;
import com.eth.web3j.service.EthTokenCollectService;
import com.eth.web3j.service.EthTransactionClientService;
import com.eth.web3j.service.EthUserWalletService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.eth.web3j.mapper.EthTokenCollectMapper;
import org.springframework.util.CollectionUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * <p>业务接口实现类</p>
 * <p>eth token 归集</p>
 *
 * @author gaog
 * @since 2021-07-17 11:03:50
 */
@Service
@Slf4j
@Transactional
public class EthTokenCollectServiceImpl extends ServiceImpl<EthTokenCollectMapper, EthTokenCollect> implements EthTokenCollectService {
    @Resource
    private EthTokenCoinConfigService ethTokenCoinConfigService;

    @Resource
    private EthTokenCollectMapper ethTokenCollectMapper;

    @Resource
    private EthTransactionClientService ethTransactionClientService;

    @Resource
    private EthUserWalletService userWalletService;


    @Value("${eth.collect.address}")
    private String COLLECT_ADDRESS;

    /**
     * 归集---主钱包归集
     * 币币转账
     */
    @Override
    public void collect() {
        Web3j web3j = Web3JClient.getClient();
        Admin admin = Web3JAdminClient.getAdminClient();

        List<EthTokenCoinConfig> list = ethTokenCoinConfigService.getEnableAllByType("eth");

        //分开处理
        list.forEach(coinConfig -> {
            List<EthTokenCollect> ethTokenCollectList = ethTokenCollectMapper.allByContract(coinConfig.getContract());
            if (ethTokenCollectList.size() != 0) {
                //处理数据
                Set<Integer> idSet = new HashSet<>();
                Set<String> addressSet = new HashSet<>();

                ethTokenCollectList.parallelStream().forEach(ethTokenCollect -> {
                    synchronized (idSet) {
                        idSet.add(ethTokenCollect.getId());
                    }
                    synchronized (addressSet) {
                        addressSet.add(ethTokenCollect.getReceiver());
                    }
                });
                if (addressSet.size() == 0) return;

                for (String address : addressSet) {
                    try {
                        int round = coinConfig.getRound();
                        BigDecimal surplus = ethTransactionClientService.getBalance(web3j, coinConfig.getContract(), round);
                        if (surplus.compareTo(BigDecimal.ZERO) == 0) {
                            ethTokenCollectMapper.updateByContractAndId(idSet, coinConfig.getContract(), address);
                            continue;
                        }
                        //通过address获取keystore
                        EthUserWallet ethUserWallet = userWalletService.getByAddress(address);
                        if (ethUserWallet==null) continue;
                        TokenTransEntranceDto transEntranceParamDto = TokenTransEntranceDto.builder()
                                .from(address)
                                .to(COLLECT_ADDRESS)
                                .password(ethUserWallet.getPassword())
                                .amount(surplus)
                                .round(round)
                                .build();
                        //查看消耗的gas
                        BigInteger tradeCost = ethTransactionClientService.getTransactionEthGaPrice(web3j);
                        if (ethTransactionClientService.enoughCost(web3j, address, tradeCost)) {
                            // 开始归集操作!
                            entrance(web3j,admin, transEntranceParamDto, coinConfig, idSet);
                        }

                    } catch (Exception e) {
                        log.error(log.getName() + " .collect, address=【{}】【{}】, 归集出现异常！", address, e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void entrance(Web3j web3j,Admin admin, TokenTransEntranceDto transEntranceParamDto, EthTokenCoinConfig ethTokenCoinConfig, Set<Integer> idSet) {
        String address = transEntranceParamDto.getTo();
        String fromAddress = transEntranceParamDto.getFrom();
        log.info(log.getName() + " .entrance, 归集开始from=【{}】 address=【{}】",fromAddress, address);
        try {
            String transactionId = ethTransactionClientService.sendTransaction(web3j,admin,transEntranceParamDto);
            log.info(log.getName() + " .entrance, 归集成功from=【{}】 address=【{}】 transactionId=【{}】", fromAddress,address, transactionId);
            baseMapper.updateByContractAndId(idSet, ethTokenCoinConfig.getContract(), address);
        } catch (Exception e) {
            e.printStackTrace();
            log.info(log.getName() + " .entrance, 归集异常from=【{}】 address=【{}】【{}】",fromAddress, address, e.getMessage());
        }
    }

}