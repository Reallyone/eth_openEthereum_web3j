/**
 * 版权声明： 版权所有 违者必究 2020
*/
package com.eth.web3j.mapper;

import com.eth.web3j.domain.EthTokenCollect;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 表说明：eth token 归集
 *
 * @since 2021-07-17 11:03:50
 */
@Mapper
public interface EthTokenCollectMapper extends BaseMapper<EthTokenCollect> {

    List<EthTokenCollect> allByContract(String contract);


    void updateByContractAndId(@Param("idSet") Set<Integer> idSet, @Param("contract") String contract, @Param("address") String address);

}