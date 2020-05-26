package com.bloxbean.kafka.connectors.web3.blocks;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.bloxbean.kafka.connectors.web3.util.ConfigConstants.VERSION;

public class BlockSourceConnector extends SourceConnector {
    private BlockSourceConfig config;

    @Override
    public void start(Map<String, String> props) {
        config = new BlockSourceConfig(props);
    }

    @Override
    public Class<? extends Task> taskClass() {
        return BlockSourceTask.class;
    }

    @Override
    public List<Map<String, String>> taskConfigs(int maxTasks) {
        // Define the individual task configurations that will be executed.
        ArrayList<Map<String, String>> configs = new ArrayList<>(1);
        configs.add(config.originalsStrings());
        return configs;
    }

    @Override
    public void stop() {
        // Do things that are necessary to stop your connector.
        // nothing is necessary to stop for this connector
    }

    @Override
    public ConfigDef config() {
        return BlockSourceConfig.conf();
    }

    @Override
    public String version() {
        return VERSION;
    }
}