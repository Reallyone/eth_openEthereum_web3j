/**
 * 版权声明： 版权所有 违者必究 2020
*/
package com.eth.web3j.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eth.web3j.domain.EthTokenRecharge;

/**
 * <p>业务接口类</p>
 * <p>用户充值,当前用户充值成功之后添加数据到这个表,充值一般无手续费.当status为0和confirm=1的时候表示充值成功</p>
 *
 * @since 2021-07-16 05:36:07
 */
public interface EthTokenRechargeService extends IService<EthTokenRecharge> {

    void rechargeJob();


}