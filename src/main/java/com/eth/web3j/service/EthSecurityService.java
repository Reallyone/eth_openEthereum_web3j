package com.eth.web3j.service;

import java.math.BigInteger;

public interface EthSecurityService {

    void exportPrivateKey(String keystorePath, String password);

    void  importPrivateKey(BigInteger privateKey, String password, String directory);
}
