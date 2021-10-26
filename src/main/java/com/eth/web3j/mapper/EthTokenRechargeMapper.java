/**
 * 版权声明： 版权所有 违者必究 2020
*/
package com.eth.web3j.mapper;

import com.eth.web3j.domain.EthTokenRecharge;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 表说明：用户充值,当前用户充值成功之后添加数据到这个表,充值一般无手续费.当status为0和confirm=1的时候表示充值成功
 *
 * @since 2021-07-16 05:36:07
 */
@Mapper
public interface EthTokenRechargeMapper extends BaseMapper<EthTokenRecharge> {
    
}