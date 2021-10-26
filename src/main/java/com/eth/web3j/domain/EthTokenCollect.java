/**
 * 版权声明： 版权所有 违者必究 2020
*/
package com.eth.web3j.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
 * <p>Table: eth_token_collect - eth token 归集</p>
 *
 * @since 2021-07-17 11:03:50
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("eth_token_collect")
public class EthTokenCollect implements Serializable {

    /** id - ID */
	@TableId(type = IdType.AUTO)
    private Integer id;

    /** receiver - 转入地址 */
    private String receiver;

    /** contract - 合约ID */
    private String contract;

    /** token - token 名称 */
    private String token;

    /** coin_id - 钱包ID */
    private Long coinId;

    /** coin_type - 钱包类型(eth、ethtoken、btc) */
    private String coinType;

    /** coin_name - 钱包名称(ETH、BTC、USDT) */
    private String coinName;

    /**
     * 数量
     */
    private BigDecimal num;

    /** status - 是否归集 O、未归集 1、已归集 */
    private Short status;

    /** remark - 备注 */
    private String remark;

    /** create_time - 创建时间 */
    private Date createTime;

    /** update_time - 更新时间 */
    private Date updateTime;

}