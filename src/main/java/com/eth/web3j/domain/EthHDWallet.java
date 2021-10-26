package com.eth.web3j.domain;

import lombok.Data;

import java.util.List;

@Data
public class EthHDWallet {

    String privateKey;

    String publicKey;

    List<String> mnemonic;

    String mnemonicPath;

    String Address;

    String keystore;

    public EthHDWallet(String privateKey, String publicKey, List<String> mnemonic, String mnemonicPath, String address, String keystore) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.mnemonic = mnemonic;
        this.mnemonicPath = mnemonicPath;
        Address = address;
        this.keystore = keystore;
    }
}
