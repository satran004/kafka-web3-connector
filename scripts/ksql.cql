
docker exec -it ksqldb-cli ksql http://ksqldb-server:8088


CREATE STREAM blocks_stream  (number INTEGER , miner VARCHAR ) with (kafka_topic='aion-latest-blocks', value_format='JSON', partitions=1);

SELECT * from blocks_stream EMIT CHANGES;


SELECT miner,
        MAX(number),
        COUNT(*) AS blockcount
   FROM blocks_stream
         WINDOW TUMBLING (SIZE 60 MINUTES)
   GROUP BY miner EMIT CHANGES;


CREATE STREAM events_stream  (blockNumber VARCHAR , address VARCHAR ) with (kafka_topic='aion-events', value_format='JSON', partitions=1);