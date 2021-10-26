/**
 * 版权声明： 版权所有 违者必究 2020
*/
package com.eth.web3j.service.impl;

import com.eth.web3j.domain.EthUserWallet;
import com.eth.web3j.service.EthUserWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eth.web3j.mapper.EthUserWalletMapper;

import javax.annotation.Resource;
import java.util.Set;


/**
 * <p>业务接口实现类</p>
 * <p>ETH 钱包</p>
 *
 * @since 2021-07-16 06:12:53
 */
@Service
@Transactional
public class EthUserWalletServiceImpl extends ServiceImpl<EthUserWalletMapper, EthUserWallet> implements EthUserWalletService {
    @Resource
    private EthUserWalletMapper ethUserWalletMapper;


    @Override
    public Set<String> existLowerAddress(Set<String> receivers) {
        return ethUserWalletMapper.existLowerAddress(receivers);
    }

    @Override
    public EthUserWallet getByAddress(String receiver) {
        return ethUserWalletMapper.getByAddress(receiver);
    }
}