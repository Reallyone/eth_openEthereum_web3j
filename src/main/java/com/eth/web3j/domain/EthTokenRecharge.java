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

import java.math.BigInteger;
import java.util.Date;
import java.math.BigDecimal;

/**
 * <p>Table: eth_token_recharge - 用户充值,当前用户充值成功之后添加数据到这个表,充值一般无手续费.当status为0和confirm=1的时候表示充值成功</p>
 *
 * @since 2021-07-16 05:36:07
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("eth_token_recharge")
public class EthTokenRecharge{

    /** id -  */
	@TableId(type = IdType.AUTO)
    private Long id;

    /** uid - 用户UID */
    private Long uid;

    /** coin_id - 币种id */
    private Long coinId;

    /** coin_name - 币种名称 */
    private String coinName;

    /** coin_type - 币种类型 */
    private String coinType;

    /** address - 钱包地址 */
    private String address;

    /** confirm - 充值确认数 */
    private Integer confirm;

    /** status - 状态：0-待入帐；1-充值成功，2到账失败，3到账成功； */
    private Integer status;

    /** txid - 交易id */
    private String txid;

    /** num - 充值量 */
    private BigDecimal num;

    /** fee - 手续费 */
    private BigDecimal fee;

    /** mum - 实际到账 */
    private BigDecimal mum;

    /** block_number - 交易的区块高度 */
    private BigInteger blockNumber;

    /** last_update_time - 修改时间 */
    private Date lastUpdateTime;

    /** created - 创建时间 */
    private Date created;

    /** type - 充值 */
    private Boolean type;

    /** remark - 备注 */
    private String remark;

}