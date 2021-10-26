/**
 * 版权声明： 版权所有 违者必究 2020
*/
package com.eth.web3j.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.math.BigInteger;
import java.util.Date;
import java.math.BigDecimal;

/**
 * <p>Table: eth_token_coin_config - eth token充值信息采集JOB表</p>
 *
 * @since 2021-07-16 03:12:15
 */
@Data
@TableName("eth_token_coin_config")
public class EthTokenCoinConfig{

    /** id - id */
	@TableId(type = IdType.AUTO)
    private Long id;

    /** coin - 币种 */
    private String coin;

    /** coin_id - 币种ID */
    private Long coinId;

    /** coin_type - 币种类型 */
    private String coinType;

    /** contract - 合约tokenID */
    private String contract;

    /** token - 合约token名称 */
    private String token;

    /** round - 小数位数 */
    private Integer round;

    /** main_address - 主钱包地址 */
    private String mainAddress;

    /** password - 主钱包密码 */
    private String password;

    /** collect_address - 归集钱包地址 */
    private String collectAddress;

    /** account_name - 账户名 */
    private String accountName;

    /** block_no - 归集的区块高度 */
    private BigInteger blockNo;

    /** valid - 是否可用：E可用，D不可用 */
    private String valid;

    /** remark - 备注 */
    private String remark;

    /** create_time - 创建时间 */
    private Date createTime;

    /** update_time - 更新时间 */
    private Date updateTime;

}