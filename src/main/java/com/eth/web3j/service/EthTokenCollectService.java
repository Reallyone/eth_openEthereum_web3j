/**
 * 版权声明： 版权所有 违者必究 2020
*/
package com.eth.web3j.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eth.web3j.domain.EthTokenCollect;

/**
 * <p>业务接口类</p>
 * <p>eth token 归集</p>
 *
 * @since 2021-07-17 11:03:50
 */
public interface EthTokenCollectService extends IService<EthTokenCollect> {

    void collect();


}