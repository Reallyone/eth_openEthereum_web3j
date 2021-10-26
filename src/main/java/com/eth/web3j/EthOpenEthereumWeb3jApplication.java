package com.eth.web3j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class EthOpenEthereumWeb3jApplication {

    public static void main(String[] args) {
        SpringApplication.run(EthOpenEthereumWeb3jApplication.class, args);
    }

}
