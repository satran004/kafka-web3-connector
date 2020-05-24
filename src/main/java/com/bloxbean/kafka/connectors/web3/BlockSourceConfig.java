package com.bloxbean.kafka.connectors.web3;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;

import java.util.Map;

import static com.bloxbean.kafka.connectors.web3.Constants.*;

public class BlockSourceConfig extends AbstractConfig {

    public BlockSourceConfig(ConfigDef config, Map<String, String> parsedConfig) {
        super(config, parsedConfig);
    }

    public BlockSourceConfig(Map<String, String> parsedConfig) {
        this(conf(), parsedConfig);
    }

    public static ConfigDef conf() {
       final ConfigDef configDef = new ConfigDef();
       configDef.define(WEB3_RPC_URL, org.apache.kafka.common.config.ConfigDef.Type.STRING, "http://localhost:8545", org.apache.kafka.common.config.ConfigDef.Importance.HIGH, "Web3 rpc address (http://<host>:<port>)");
       configDef.define(TOPIC, org.apache.kafka.common.config.ConfigDef.Type.STRING, "", org.apache.kafka.common.config.ConfigDef.Importance.HIGH, "Destination topic");
       configDef.define(START_BLOCK, ConfigDef.Type.LONG, 0, org.apache.kafka.common.config.ConfigDef.Importance.HIGH, "Start Block Number");
       configDef.define(BLOCK_TIME_IN_SEC, ConfigDef.Type.INT, 10, org.apache.kafka.common.config.ConfigDef.Importance.HIGH, "Block time in sec");
       configDef.define(NO_BLOCKS_FOR_FINALITY, ConfigDef.Type.INT, 0, org.apache.kafka.common.config.ConfigDef.Importance.HIGH, "No of blocks to wait for finality");

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
}
