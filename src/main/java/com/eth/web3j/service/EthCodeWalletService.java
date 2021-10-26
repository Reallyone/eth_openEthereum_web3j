package com.eth.web3j.service;

import com.eth.web3j.domain.vo.ETHWalletDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.web3j.crypto.*;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Map;

/**
 * 冷钱包-操作
 */
public interface EthCodeWalletService {

   /**
    * 创建钱包地址
    * @param password
    * @return
    */
   ETHWalletDto createWallet(String password);

   ETHWalletDto createWallet();

   /**
    * 解密keystore 得到私钥
    *
    * @param keystore
    * @param password
    */
   String decryptWallet(String keystore, String password);

   /**
    * 签名交易
    */
   String signTransaction(BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to,
                          BigInteger value, String data, byte chainId, String privateKey) throws IOException;

}
