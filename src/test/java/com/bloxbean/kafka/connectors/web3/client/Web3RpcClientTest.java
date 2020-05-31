package com.bloxbean.kafka.connectors.web3.client;

import com.bloxbean.kafka.connectors.web3.util.HexConverter;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.junit.jupiter.api.Test;

class Web3RpcClientTest {

    @Test
    void getLatestBlock() {
    }

    @Test
    void getBlockByNumber() {
//        long blockNumber = 6139184;
//        Web3RpcClient web3RpcClient = new Web3RpcClient("http://localhost:8545");
//        JSONObject jsonObject = web3RpcClient.getBlockByNumber(String.valueOf(blockNumber), true);
//
//        System.out.println(jsonObject);
//        System.out.println(jsonObject.getString("timestamp"));
//        System.out.println(HexConverter.hexToTimestampInMillis(jsonObject.getString("timestamp")));
    }

    @Test
    void getLogs() {
//        long blockNumber = 0L;
//        Web3RpcClient web3RpcClient = new Web3RpcClient("http://localhost:8545");
//        JSONArray jsonArray = web3RpcClient.getLogs("6117319", "6117350", null, null, null);
//
//        System.out.println(jsonArray);
    }
}