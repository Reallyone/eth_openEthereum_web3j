package com.eth.web3j.core;

import com.eth.web3j.common.Environment;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.http.HttpService;

public class Web3JAdminClient {

    private Web3JAdminClient() {
    }

    private volatile static Admin admin;

    public static  Admin getAdminClient() {
        if (admin == null) {
            synchronized (Web3JClient.class) {
                if (admin == null) {
                    admin =  Admin.build(new HttpService(Environment.RPC_URL));
                }
            }
        }
        return admin;
    }

}
