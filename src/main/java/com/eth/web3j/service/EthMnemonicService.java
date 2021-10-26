package com.eth.web3j.service;

import com.eth.web3j.domain.EthHDWallet;

import java.util.List;

/**
 * 以太坊助记词
 * 用到了比特币的jar包 org.bitcoinj
 */
public interface EthMnemonicService {

    EthHDWallet generateMnemonic(String path, String password);

    EthHDWallet importMnemonic(String path, List<String> list, String password);


}
