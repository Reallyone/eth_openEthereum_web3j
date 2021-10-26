/**
 * 版权声明： 版权所有 违者必究 2020
*/
package com.eth.web3j.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eth.web3j.domain.EthTokenAddressPool;

/**
 * <p>业务接口类</p>
 * <p>用户的地址池</p>
 *
 * @since 2021-07-16 02:54:21
 */
public interface EthTokenAddressPoolService extends IService<EthTokenAddressPool> {

    void batchCreateEthAddress();


}