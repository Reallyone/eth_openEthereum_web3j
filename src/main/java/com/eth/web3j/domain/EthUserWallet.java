/**
 * 版权声明： 版权所有 违者必究 2020
*/
package com.eth.web3j.domain;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.util.Date;
import java.math.BigDecimal;

/**
 * <p>Table: eth_user_wallet - ETH 钱包</p>
 *
 * @author gaog
 * @since 2021-07-16 06:12:53
 */
@Data
public class EthUserWallet{

    /** id - ID[钱包名] */
	@TableId(type = IdType.AUTO)
    private Long id;

    /** uid - 用户ID */
    private Long uid;

    /** coin_id - 币种ID */
    private Long coinId;

    /** address - 钱包地址 */
    private String address;

    /** lower_address - 小写地址 */
    private String lowerAddress;

    /** password - 钱包密码 */
    private String password;

    /** keystore - 秘钥文件 */
    private String keystore;

    /** valid - 是否可用：E可用，D不可用 */
    private String valid;

    /** create_time - 创建时间 */
    private Date createTime;

    /** update_time - 更新时间 */
    private Date updateTime;

}