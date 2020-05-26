package com.bloxbean.kafka.connectors.web3.client;

import com.bloxbean.kafka.connectors.web3.exception.Web3Exception;
import kong.unirest.*;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Web3RpcClient {
    private Logger log = LoggerFactory.getLogger(Web3RpcClient.class);

    private String web3RpcUrl;

    public Web3RpcClient(String web3RpcUrl) {
        this.web3RpcUrl = web3RpcUrl;
    }

    public JSONObject getBlockByNumber(String blockNumber, boolean fullTxnObject) {
        try {
            JSONObject jo = getJsonHeader("eth_getBlockByNumber");

            List<String> params = new ArrayList<>();
            params.add(blockNumber);
            params.add(Boolean.toString(fullTxnObject));
            jo.put("params", params);

            HttpResponse<JsonNode> jsonResponse = getHttpRequest()
                    .body(jo)
                    .asJson();

            JsonNode jsonNode = jsonResponse.getBody();

            JSONObject result = (JSONObject) jsonNode.getObject().get("result");

            return result;
        } catch (UnirestException e) {
            throw new Web3Exception("Web3Rpc call failed to get block by number", e);
        }
    }

    public String getLatestBlock() {

        try {
            JSONObject jo = getJsonHeader("eth_blockNumber");
            jo.put("params", Collections.EMPTY_LIST);

            HttpResponse<JsonNode> jsonResponse = getHttpRequest()
                    .body(jo)
                    .asJson();

            JsonNode jsonNode = jsonResponse.getBody();

            Object blockNumber = jsonNode.getObject().get("result");

            return blockNumber != null ? blockNumber.toString() : null;
        } catch (UnirestException e) {
            throw new Web3Exception("Web3Rpc call failed to get latest block", e);
        }
    }

    public JSONArray getLogs(String fromBlock, String toBlock, String addresses, String topics, String blockHash) {
        try {
            JSONObject jo = getJsonHeader("eth_getLogs");
            List<JSONObject> params = new ArrayList();

            JSONObject filters = new JSONObject();

            if (fromBlock != null && !fromBlock.trim().isEmpty())
                filters.put("fromBlock", fromBlock);

            if (toBlock != null && !toBlock.trim().isEmpty())
                filters.put("toBlock", toBlock);

            if (addresses != null && !addresses.trim().isEmpty()) {
                //split addresses
                String[] addArray = addresses.split(",");

                JSONArray jsonArray = new JSONArray();
                for (String address : addArray) {
                    jsonArray.put(address.trim());
                }

                filters.put("address", jsonArray);
            }

            if (topics != null && !topics.trim().isEmpty()) {
                //split topics
                String[] topicsArray = topics.split(",");

                JSONArray jsonArray = new JSONArray();
                for (String topic : topicsArray) {
                    jsonArray.put(topic);
                }

                filters.put("topics", jsonArray);
            }

            if (blockHash != null && !blockHash.isEmpty()) {
                filters.put("blockhash", blockHash);

            }
            params.add(filters);

            jo.put("params", params);

            if(log.isDebugEnabled())
                log.debug("Web3Rpc request data: \n" + jo.toString(2));

            HttpResponse<JsonNode> jsonResponse = getHttpRequest()
                    .body(jo)
                    .asJson();

            JsonNode jsonNode = jsonResponse.getBody();

            if (jsonNode == null)
                return null;

            if(log.isDebugEnabled())
                log.debug("Response from Aion kernel: \n" + jsonNode.getObject().toString(2));

            JSONObject jsonObject = jsonNode.getObject();

            String error = getError(jsonObject);

            if (error == null) {
                return jsonObject.getJSONArray("result");
            } else {
                throw new Web3Exception("getLogs() failed. Reason: " + error);
            }

        } catch (UnirestException e) {
            throw new Web3Exception("getLogs() failed", e);
        }
    }

    private String getError(JSONObject jsonObject) {
        JSONObject error = jsonObject.optJSONObject("error");

        if (error != null)
            return error.toString();
        else
            return null;
    }

    private JSONObject getJsonHeader(String method) {
        JSONObject jo = new JSONObject();
        jo.put("id", generateRandomId());
        jo.put("jsonrpc", "2.0");
        jo.put("method", method);
        return jo;
    }

    private String generateRandomId() {
        Random rand = new Random();
        int randInt = rand.nextInt(1000000);
        return String.valueOf(randInt);
    }

    private HttpRequestWithBody getHttpRequest() {
        return Unirest.post(web3RpcUrl)
                .header("accept", "application/json")
                .header("Content-Type", "application/json");
    }
}
