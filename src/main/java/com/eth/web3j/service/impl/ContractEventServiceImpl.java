package com.eth.web3j.service.impl;

import com.eth.web3j.service.ContractEventService;
import org.springframework.stereotype.Service;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;

import java.util.Arrays;
import java.util.List;

@Service
public class ContractEventServiceImpl implements ContractEventService {


    @Override
    public void erc20TokenEvent(Web3j web3j, String contractAddress) {
        /**
         * 监听ERC20 token 交易
         */
        EthFilter filter = new EthFilter(
                DefaultBlockParameterName.EARLIEST,
                DefaultBlockParameterName.LATEST,
                contractAddress);
        Event event = new Event("Transfer",
                Arrays.<TypeReference<?>>asList(
                        new TypeReference<Address>(true) {
                        },
                        new TypeReference<Address>(true) {
                        }, new TypeReference<Uint256>(false) {
                        }
                )
        );

        String topicData = EventEncoder.encode(event);
        filter.addSingleTopic(topicData);
        System.out.println(topicData);

        web3j.ethLogFlowable(filter).subscribe(log -> {
            System.out.println(log.getBlockNumber());
            System.out.println(log.getTransactionHash());
            List<String> topics = log.getTopics();
            for (String topic : topics) {
                System.out.println(topic);
            }
        });
    }
}
