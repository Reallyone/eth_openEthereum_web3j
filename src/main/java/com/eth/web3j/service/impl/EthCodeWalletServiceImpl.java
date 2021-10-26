package com.eth.web3j.service.impl;

import com.alibaba.fastjson.JSON;
import com.eth.web3j.domain.vo.ETHWalletDto;
import com.eth.web3j.service.EthCodeWalletService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.web3j.crypto.*;
import org.web3j.protocol.ObjectMapperFactory;
import org.web3j.tx.ChainId;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class EthCodeWalletServiceImpl implements EthCodeWalletService {

    /**
     * 创建钱包
     *
     * @param password 密码
     */
    public  ETHWalletDto createWallet(String password) {
        try {
            WalletFile walletFile;
            ECKeyPair ecKeyPair = Keys.createEcKeyPair();
            walletFile = Wallet.createStandard(password, ecKeyPair);

            String address = "0x" + walletFile.getAddress();
            String keystore = JSON.toJSONString(walletFile);

            return ETHWalletDto.builder()
                    .password(password)
                    .address(address)
                    .privateKey(keystore)
                    .build();
        }catch (Exception e){
            log.error(log.getName() + ".createWallet.error", e);
            throw new RuntimeException("创建钱包失败");
        }
    }


    /**
     * 创建钱包 --ETHWalletDto
     *
     * @return
     */
    @Override
    public ETHWalletDto createWallet() {
        String password = UUID.randomUUID().toString().replaceAll("-", "");
        try {
            ECKeyPair ecKeyPair = Keys.createEcKeyPair();
            WalletFile walletFile = Wallet.createLight(password, ecKeyPair);
            String address = "0x" + walletFile.getAddress();
            String keystore = JSON.toJSONString(walletFile);
            return ETHWalletDto.builder()
                    .password(password)
                    .address(address)
                    .privateKey(keystore)
                    .build();
        } catch (Exception e) {
            log.error(log.getName() + ".createWallet.error", e);
            throw new RuntimeException("创建钱包失败");
        }
    }

    /**
     * 解密keystore 得到私钥
     *
     * @param keystore
     * @param password
     */
    @Override
    public String decryptWallet(String keystore, String password) {
        String privateKey = null;
        ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
        try {
            WalletFile walletFile = objectMapper.readValue(keystore, WalletFile.class);
            ECKeyPair ecKeyPair = null;
            ecKeyPair = Wallet.decrypt(password, walletFile);
            privateKey = ecKeyPair.getPrivateKey().toString(16);
            System.out.println(privateKey);
        } catch (CipherException e) {
            if ("Invalid password provided".equals(e.getMessage())) {
                System.out.println("密码错误");
            }
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return privateKey;
    }


    /**
     * 签名交易
     */
    @Override
    public String signTransaction(BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to, BigInteger value, String data, byte chainId, String privateKey) throws IOException {
        byte[] signedMessage;
        RawTransaction rawTransaction = RawTransaction.createTransaction(
                nonce,
                gasPrice,
                gasLimit,
                to,
                value,
                data);

        if (privateKey.startsWith("0x")) {
            privateKey = privateKey.substring(2);
        }
        ECKeyPair ecKeyPair = ECKeyPair.create(new BigInteger(privateKey, 16));
        Credentials credentials = Credentials.create(ecKeyPair);

        if (chainId > ChainId.NONE) {
            signedMessage = TransactionEncoder.signMessage(rawTransaction, chainId, credentials);
        } else {
            signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        }
        String hexValue = Numeric.toHexString(signedMessage);
        return hexValue;
    }

}
