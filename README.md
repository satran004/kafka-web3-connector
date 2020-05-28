![Java CI with Maven](https://github.com/satran004/kafka-web3-connector/workflows/Java%20CI%20with%20Maven/badge.svg)

# Kafka Web3 Connector

This connector reads blocks or events from a web3 json rpc compatible blockchain (Example: Aion) and pushes them to Kafka.

There are two available source connectors
1. **Block Source Connector :** com.bloxbean.kafka.connectors.web3.source.blocks.BlockSourceConnector
2. **Event Logs Connector :** com.bloxbean.kafka.connectors.web3.source.events.EventSourceConnector
## Build

    >mvn clean package
        
## Running the Block Source connector

1. Block Source Connector properties

```$xslt
name=bloxbean-web3-source-connector
connector.class=com.bloxbean.kafka.connectors.web3.source.blocks.BlockSourceConnector
tasks.max=1
web3_rpc_url=http://<web3_rpc_host>:<port>
topic=web3-connect-test
start_block=6106120
block_time=10
```
   
2. Start the connector

   >cd ../kafka-web3-connector
   
   >$KAFKA_HOME/bin/connect-standalone.sh config/connect-standalone.properties config/connector-web3-blocks-source.properties
     
## Running the Event Logs Source connector

1. Event Logs Source Connector properties

```$xslt
name=bloxbean-web3-events-source-connector
connector.class=com.bloxbean.kafka.connectors.web3.source.events.EventSourceConnector
tasks.max=1
web3_rpc_url=http://192.168.0.96:8545
topic=web3-events
start_block=6117319
block_time=10
no_of_blocks_for_finality=30

event_logs_filter_addresses=0xa008e42a76e2e779175c589efdb2a0e742b40d8d421df2b93a8a0b13090c7cc8
event_logs_filter_topics=0x41445344656c6567617465640000000000000000000000000000000000000000

####################################################################################
# Target kafka topic's key
# Comma separated list of following options
# Options: blockNumber, logIndex, address, topic, transactionHash, transactionIndex
# Default: transactionHash,logIndex
####################################################################################
#event_logs_kafka_keys=  
```
   
2. Start the connector

   >cd ../kafka-web3-connector
   
   >$KAFKA_HOME/bin/connect-standalone.sh config/connect-standalone.properties config/connector-web3-events-source.properties
     
