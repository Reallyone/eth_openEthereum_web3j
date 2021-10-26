/**
 * 版权声明： 版权所有 违者必究 2020
*/
package com.eth.web3j.mapper;

import com.eth.web3j.domain.EthTokenAddressPool;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 表说明：用户的地址池
 *
 *
 * @since 2021-07-16 02:54:21
 */
@Mapper
public interface EthTokenAddressPoolMapper extends BaseMapper<EthTokenAddressPool> {
    
}