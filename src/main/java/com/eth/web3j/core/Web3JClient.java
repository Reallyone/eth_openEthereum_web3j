package com.eth.web3j.core;

import com.eth.web3j.common.Environment;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

public class Web3JClient {

    private Web3JClient() {
    }

    private volatile static Web3j web3j;

    public static Web3j getClient() {
        if (web3j == null) {
            synchronized (Web3JClient.class) {
                if (web3j == null) {
                    web3j = Web3j.build(new HttpService(Environment.RPC_URL));
                }
            }
        }
        return web3j;
    }

}
