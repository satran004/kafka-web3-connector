package com.bloxbean.web3.kafka_connector.util;

import com.bloxbean.kafka.connectors.web3.client.Web3RpcClient;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.apache.commons.codec.binary.Hex;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Web3RpcClientTest {

    @Test
    void getLatestBlock() {
    }

    @Test
    void getBlockByNumber() {
//        long blockNumber = 0L;
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