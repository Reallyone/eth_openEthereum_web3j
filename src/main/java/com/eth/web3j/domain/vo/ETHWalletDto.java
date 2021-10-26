package com.eth.web3j.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 下单请求参数
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ETHWalletDto {

    private String password;//钱包密码
    private String address;//钱包地址
    private String privateKey;//秘钥

}
