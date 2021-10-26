/**
 * 版权声明： 版权所有 违者必究 2020
*/
package com.eth.web3j.mapper;

import com.eth.web3j.domain.EthTokenCoinConfig;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.util.List;

/**
 * 表说明：eth token充值信息采集JOB表
 *
 * @author gaog
 * @since 2021-07-16 03:12:15
 */
@Mapper
public interface EthTokenCoinConfigMapper extends BaseMapper<EthTokenCoinConfig> {

    List<EthTokenCoinConfig> getEnableAllByType(String type);

    int updateActionSeqById(@Param("id") Long id, @Param("blockNo") BigInteger blockNo);

}