package com.bloxbean.kafka.connectors.web3.blocks;

import com.bloxbean.kafka.connectors.web3.exception.Web3Exception;
import com.bloxbean.kafka.connectors.web3.util.ConfigConstants;
import com.bloxbean.kafka.connectors.web3.client.Web3RpcClient;
import com.bloxbean.kafka.connectors.web3.util.HexConverter;
import com.bloxbean.kafka.connectors.web3.exception.Web3ConnectorException;
import com.bloxbean.kafka.connectors.web3.util.StringUtil;
import kong.unirest.json.JSONObject;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static com.bloxbean.kafka.connectors.web3.util.ConfigConstants.LAST_FETCHED_BLOCK_NUMBER;
import static com.bloxbean.kafka.connectors.web3.util.ConfigConstants.*;

public class BlockSourceTask extends SourceTask {
    private static Logger logger = LoggerFactory.getLogger(BlockSourceTask.class);
    private static int RETRY_THRESHOLD = 10;
    private static int DELAY_AFTER_RETRY_THRESHOLD = 30 * 1000; //Retry every 30 sec after retry threshhold

    private BlockSourceConfig config;
    private Web3RpcClient web3RpcClient;

    private long newBlockWaitTime = 0;
    private long blockNumberOffset;

    private int retryCounter;

    public String version() {
        return ConfigConstants.VERSION;
    }

    public void start(Map<String, String> map) {
        //Find last offset or blocknumber
        config = new BlockSourceConfig(map);
        initializeLastVariables();
        web3RpcClient = new Web3RpcClient(config.getWeb3RpcUrl());
    }

    private void initializeLastVariables() {
        //Initialize variables for the last run
        Map<String, Object> lastSourceOffset = null;
        lastSourceOffset = context.offsetStorageReader().offset(sourcePartition());
        if (lastSourceOffset == null) {
            // we haven't fetched anything yet, so we initialize to START_BLOCK
            blockNumberOffset = config.getStartBlock();
        } else {
            String lastFetchedBlockNumber = (String)lastSourceOffset.get(LAST_FETCHED_BLOCK_NUMBER);
            if(lastFetchedBlockNumber != null && lastFetchedBlockNumber.length() > 0)
                blockNumberOffset = Long.parseLong(lastFetchedBlockNumber) + 1;
            else
                throw new Web3ConnectorException(String.format("Invalid last fetched block number : %s", lastFetchedBlockNumber));
        }
        newBlockWaitTime = config.getBlockTime() * 1000;
    }

    public List<SourceRecord> poll() throws InterruptedException {
        try {
            if(!canContinue(blockNumberOffset)) {//Wait. May be finality not reached.
                Thread.sleep(newBlockWaitTime);
                return Collections.EMPTY_LIST;
            }

            JSONObject jsonObject = web3RpcClient.getBlockByNumber(String.valueOf(blockNumberOffset), true);
            if (jsonObject == null) {
                logger.info("Unable to fetch blocks from blockchain. Let's wait for {} sec to get the new block : {}", newBlockWaitTime/1000, blockNumberOffset);
                Thread.sleep(newBlockWaitTime);
                return Collections.EMPTY_LIST;
            }

            List<SourceRecord> sourceRecords = new ArrayList<>();

            long timestamp = HexConverter.hexToTimestampInMillis(jsonObject.getString("timestamp"));
            SourceRecord sourceRecord = generateSourceRecord(jsonObject, blockNumberOffset, timestamp);
            sourceRecords.add(sourceRecord);

            logger.info("Successfully fetched block : {} ", jsonObject.getString("number"));

            blockNumberOffset++;
            retryCounter = 0;

            return sourceRecords;
        } catch (Web3ConnectorException | Web3Exception ex) {
            if(!isRetryThresholdReachedAndwaitDuringRetry()) {
                logger.error("Error getting data through web3 client", ex);
            } else {
                logger.error("Error getting data through web3 client", ex.getMessage());
            }
            return null;
        } catch (Exception ex) {
            if(!isRetryThresholdReachedAndwaitDuringRetry()) {
                logger.error("System Error", ex);
            } else {
                logger.error("System Error", ex.getMessage());
            }
            return null;
        }
    }

    private boolean canContinue(long blockNumber) {
        int finalityBlocksNo = config.getNoBlocksForFinality();
        if(finalityBlocksNo == 0) {
            return true;
        } else {
            //Wait for finality blocks.
            //Get latest block and see the difference
            String latestBlockNumberStr = web3RpcClient.getLatestBlock();
            if(StringUtil.isEmpty(latestBlockNumberStr)) {
                logger.error("Unable to get latest block number");
                return false;
            } else {
                long latestBlockNumber = Long.parseLong(latestBlockNumberStr);
                if(finalityBlocksNo <= (latestBlockNumber - blockNumber)) {
                    return true;
                } else {
                    logger.info("Wait for finality !!! BlockNumberOffset :{}, Latest Block# on chain: {}", blockNumberOffset, latestBlockNumber);
                    return false;
                }
            }
        }
    }

    private SourceRecord generateSourceRecord(JSONObject blockJson, long blockNumberOffset, long timestamp) {
        return new SourceRecord(
                sourcePartition(),
                sourceOffset(blockNumberOffset),
                config.getTopic(),
                null, // partition will be inferred by the framework
                null,
                String.valueOf(blockNumberOffset),
                null,
                blockJson.toString(),
                timestamp
                );
    }

    private Map<String, String> sourcePartition() {
        Map<String, String> map = new HashMap<>();
        map.put(WEB3_RPC_URL, config.getWeb3RpcUrl());
        return map;
    }

    private Map<String, String> sourceOffset(long blockNumber) {
        Map<String, String> map = new HashMap<>();
        map.put(LAST_FETCHED_BLOCK_NUMBER, String.valueOf(blockNumber));
        return map;
    }

    private boolean isRetryThresholdReachedAndwaitDuringRetry() throws InterruptedException {
        retryCounter ++;
        if(retryCounter >= RETRY_THRESHOLD) {
            Thread.sleep(DELAY_AFTER_RETRY_THRESHOLD);
            return true;
        } else {
            Thread.sleep(10000);
            return false;
        }
    }

    public void stop() {
        //Do to stop the task
    }
}
