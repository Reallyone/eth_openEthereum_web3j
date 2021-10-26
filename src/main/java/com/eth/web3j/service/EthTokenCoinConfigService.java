/**
 * 版权声明： 版权所有 违者必究 2020
*/
package com.eth.web3j.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eth.web3j.domain.EthTokenCoinConfig;

import java.util.List;

/**
 * <p>业务接口类</p>
 * <p>eth token充值信息采集JOB表</p>
 * @since 2021-07-16 03:12:15
 */
public interface EthTokenCoinConfigService extends IService<EthTokenCoinConfig> {

    List<EthTokenCoinConfig> getEnableAllByType(String type);
}