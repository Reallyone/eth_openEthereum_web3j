/**
 * 版权声明： 版权所有 违者必究 2020
*/
package com.eth.web3j.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eth.web3j.domain.EthUserWallet;

import java.util.Set;

/**
 * <p>业务接口类</p>
 * <p>ETH 钱包</p>
 *
 * @since 2021-07-16 06:12:53
 */
public interface EthUserWalletService extends IService<EthUserWallet> {

    Set<String> existLowerAddress(Set<String> receivers);


    EthUserWallet getByAddress(String receiver);


}