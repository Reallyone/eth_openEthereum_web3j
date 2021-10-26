package com.eth.web3j.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 通过合约转账
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenTransEntranceDto {

    String from;//转出方账号
    String to;//转入方账号
    String password; //keystore
    BigDecimal amount;//数量
    int round;//token小数位数
}

