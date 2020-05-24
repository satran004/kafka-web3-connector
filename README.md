# Kafka Web3 Connector

This connector reads blocks from a web3 json rpc compatible blockchain and pushes them to Kafka.

## Build

    >mvn clean package
        
## Running the connector

1. Connector properties

```$xslt
name=bloxbean-web3-source-connector
connector.class=com.bloxbean.kafka.connectors.web3.BlockSourceConnector
tasks.max=1
web3_rpc_url=http://<web3_rpc_host>:<port>
topic=web3-connect-test
start_block=6106120
block_time=10
```
   
2. Start the connector

   >cd ../kafka-web3-connector
   >$KAFKA_HOME/bin/connect-standalone.sh config/connect-standalone.properties config/connector-web3-source.properties
     
