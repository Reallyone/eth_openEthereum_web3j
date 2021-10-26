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
import java.math.BigDecimal;

/**
 * <p>Table: eth_token_address_pool - 用户的地址池</p>
 *
 * @since 2021-07-16 02:54:21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("eth_token_address_pool")
public class EthTokenAddressPool implements Serializable {
    private static final long serialVersionUID = 1L;

    /** id -  */
	@TableId(type = IdType.AUTO)
    private Long id;

    /** coin_id - 币种ID */
    private Long coinId;

    /** address - 地址 */
    private String address;

    /** keystore - keystore */
    private String keystore;

    /** pwd - 密码 */
    private String pwd;

    /** coin_type - 地址类型 */
    private String coinType;

}