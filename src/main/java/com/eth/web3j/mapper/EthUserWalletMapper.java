/**
 * 版权声明： 版权所有 违者必究 2020
*/
package com.eth.web3j.mapper;

import com.eth.web3j.domain.EthUserWallet;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * 表说明：ETH 钱包
 *
 * @author gaog
 * @since 2021-07-16 06:12:53
 */
@Mapper
public interface EthUserWalletMapper extends BaseMapper<EthUserWallet> {

    Set<String> existLowerAddress(@Param("addressSet") Set<String> receivers);

    EthUserWallet getByAddress(String address);

}