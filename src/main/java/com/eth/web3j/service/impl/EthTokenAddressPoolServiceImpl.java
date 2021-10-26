/**
 * 版权声明： 版权所有 违者必究 2020
 */
package com.eth.web3j.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eth.web3j.domain.EthTokenAddressPool;
import com.eth.web3j.domain.EthTokenCoinConfig;
import com.eth.web3j.domain.vo.ETHWalletDto;
import com.eth.web3j.mapper.EthTokenAddressPoolMapper;
import com.eth.web3j.service.EthCodeWalletService;
import com.eth.web3j.service.EthTokenAddressPoolService;
import com.eth.web3j.service.EthTokenCoinConfigService;
import com.eth.web3j.util.Help;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * <p>业务接口实现类</p>
 * <p>用户的地址池</p>
 *
 * @author gaog
 * @since 2021-07-16 02:54:21
 */
@Service
@Transactional
@Slf4j
public class EthTokenAddressPoolServiceImpl extends ServiceImpl<EthTokenAddressPoolMapper, EthTokenAddressPool> implements EthTokenAddressPoolService {

    @Resource
    private EthTokenCoinConfigService ethTokenCoinConfigService;

    @Resource
    private EthCodeWalletService ethCodeWalletService;

    @Override
    public void batchCreateEthAddress() {
        List<EthTokenCoinConfig> ethRechargeJobList = ethTokenCoinConfigService.getEnableAllByType("eth");
        for (EthTokenCoinConfig e : ethRechargeJobList) {
            try {
                QueryWrapper<EthTokenAddressPool> tWrapper = new QueryWrapper<>();
                tWrapper.eq("coin_type", "eth");
                Integer count = baseMapper.selectCount(tWrapper);

                if (Help.isNull(count)) {
                    count = 0;
                }
                if (count < 100) {
                    log.info(log.getName() + " 创建地址池开始, size【{}】", 100 - count);
                    for (int i = 0; i < 100 - count; i++) {
                        ETHWalletDto ethWalletDto = ethCodeWalletService.createWallet();
                        baseMapper.insert(EthTokenAddressPool.builder()
                                .coinType("eth")
                                .coinId(e.getId())
                                .address(ethWalletDto.getAddress())
                                .keystore(ethWalletDto.getPrivateKey())
                                .pwd(ethWalletDto.getPassword())
                                .build());
                    }
                }
            } catch (Exception ex) {
                log.error(log.getName() + " 创建地址池出现异常:【{}】", ex.getMessage());
                ex.printStackTrace();
            }
        }

    }


}