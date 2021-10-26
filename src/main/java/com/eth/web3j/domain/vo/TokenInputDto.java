package com.eth.web3j.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 下单请求参数
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenInputDto {

    private BigInteger blockNo;//区块高度
    private String sender;//发送地址
    private String receiver;//接收地址
    private BigDecimal amount;//交易数额
    private Boolean isContract;//是否合约交易
    private String contract;//合约信息
}
