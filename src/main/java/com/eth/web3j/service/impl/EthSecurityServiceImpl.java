package com.eth.web3j.service.impl;

import com.eth.web3j.service.EthSecurityService;
import org.springframework.stereotype.Service;
import org.web3j.crypto.*;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

@Service
public class EthSecurityServiceImpl implements EthSecurityService {

    /**
     * 导出私钥
     *
     * @param keystorePath 账号的keystore路径
     * @param password     密码
     */
    @Override
    public   void exportPrivateKey(String keystorePath, String password) {
        try {
            Credentials credentials = WalletUtils.loadCredentials(
                    password,
                    keystorePath);
            BigInteger privateKey = credentials.getEcKeyPair().getPrivateKey();
            System.out.println(privateKey.toString(16));
        } catch (IOException | CipherException e) {
            e.printStackTrace();
        }
    }


    /**
     * 导入私钥
     *
     * @param privateKey 私钥
     * @param password   密码
     * @param directory  存储路径 默认测试网络WalletUtils.getTestnetKeyDirectory() 默认主网络 WalletUtils.getMainnetKeyDirectory()
     */
    @Override
    public   void importPrivateKey(BigInteger privateKey, String password, String directory) {
        ECKeyPair ecKeyPair = ECKeyPair.create(privateKey);
        try {
            String keystoreName = WalletUtils.generateWalletFile(password,
                    ecKeyPair,
                    new File(directory),
                    true);
            System.out.println("keystore name " + keystoreName);
        } catch (CipherException | IOException e) {
            e.printStackTrace();
        }
    }


}
