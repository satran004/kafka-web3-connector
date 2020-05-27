package com.bloxbean.kafka.connectors.web3.source.events;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.bloxbean.kafka.connectors.web3.util.ConfigConstants.*;
import static com.bloxbean.kafka.connectors.web3.util.ConfigConstants.NO_BLOCKS_FOR_FINALITY;

public class EventSourceConfig extends AbstractConfig {
    private final static List<String> DEFAULT_KAFKA_KEYS = Arrays.asList("transactionHash","logIndex");

    public EventSourceConfig(ConfigDef config, Map<String, String> parsedConfig) {
        super(config, parsedConfig);
    }

    public EventSourceConfig(Map<String, String> parsedConfig) {
        super(conf(), parsedConfig);
    }

    public static ConfigDef conf() {
        final ConfigDef configDef = new ConfigDef();
        configDef.define(WEB3_RPC_URL, org.apache.kafka.common.config.ConfigDef.Type.STRING, "http://localhost:8545", org.apache.kafka.common.config.ConfigDef.Importance.HIGH, "Web3 rpc address (http://<host>:<port>)");
        configDef.define(TOPIC, org.apache.kafka.common.config.ConfigDef.Type.STRING, "", org.apache.kafka.common.config.ConfigDef.Importance.HIGH, "Destination topic");
        configDef.define(START_BLOCK, ConfigDef.Type.LONG, 0, org.apache.kafka.common.config.ConfigDef.Importance.HIGH, "Start Block Number");
        configDef.define(BLOCK_TIME_IN_SEC, ConfigDef.Type.INT, 10, org.apache.kafka.common.config.ConfigDef.Importance.HIGH, "Block time in sec");
        configDef.define(NO_BLOCKS_FOR_FINALITY, ConfigDef.Type.INT, 0, org.apache.kafka.common.config.ConfigDef.Importance.HIGH, "No of blocks to wait for finality");

        configDef.define(EVENT_LOGS_FILTER_ADDRESSES, org.apache.kafka.common.config.ConfigDef.Type.STRING, "", org.apache.kafka.common.config.ConfigDef.Importance.HIGH, "Contract address or a list of addresses from which logs should originate");
        configDef.define(EVENT_LOGS_FILTER_TOPICS, org.apache.kafka.common.config.ConfigDef.Type.STRING, "", org.apache.kafka.common.config.ConfigDef.Importance.HIGH, "Topic or list of topics");
        configDef.define(EVENT_LOGS_KAFKA_KEYS, ConfigDef.Type.LIST, DEFAULT_KAFKA_KEYS,
                org.apache.kafka.common.config.ConfigDef.Importance.HIGH, "Comma separated topic keys. Default: address. Options:  blockNumber, logIndex, address, topic, transactonHash, transactionIndex");

        return configDef;
    }

    public String getWeb3RpcUrl() {
        return getString(WEB3_RPC_URL);
    }

    public String getTopic() {
        return getString(TOPIC);
    }

    public long getStartBlock() {
        return getLong(START_BLOCK);
    }

    public int getBlockTime() {
        return getInt(BLOCK_TIME_IN_SEC);
    }

    public int getNoBlocksForFinality() {
        return getInt(NO_BLOCKS_FOR_FINALITY);
    }

    public String getEventLogsFilterAddresses() {
        return getString(EVENT_LOGS_FILTER_ADDRESSES);
    }

    public String getEventLogsFilterTopics() {
        return getString(EVENT_LOGS_FILTER_TOPICS);
    }

    public List<String> getEventLogsKafkaKeys() {
        List<String> keys = getList(EVENT_LOGS_KAFKA_KEYS);
        if(keys != null && !keys.isEmpty())
            return keys;
        else
            return DEFAULT_KAFKA_KEYS;
    }
}
