/**
 * 版权声明： 版权所有 违者必究 2020
*/
package com.eth.web3j.service.impl;

import com.eth.web3j.domain.EthTokenCoinConfig;
import com.eth.web3j.service.EthTokenCoinConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eth.web3j.mapper.EthTokenCoinConfigMapper;

import javax.annotation.Resource;
import java.util.List;


/**
 * <p>业务接口实现类</p>
 * <p>eth token充值信息采集JOB表</p>
 *
 * @since 2021-07-16 03:12:15
 */
@Service
@Transactional
public class EthTokenCoinConfigServiceImpl extends ServiceImpl<EthTokenCoinConfigMapper, EthTokenCoinConfig> implements EthTokenCoinConfigService {

    @Resource
    private EthTokenCoinConfigMapper ethTokenCoinConfigMapper;

    @Override
    public List<EthTokenCoinConfig> getEnableAllByType(String type) {
        return ethTokenCoinConfigMapper.getEnableAllByType(type);
    }

}