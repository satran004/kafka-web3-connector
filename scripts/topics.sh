bin/kafka-topics.sh --create --zookeeper localhost:2181 --topic aion-blocks --replication-factor 1 --partitions 1 --config "cleanup.policy=compact"
bin/kafka-topics.sh --create --zookeeper localhost:2181 --topic aion-latest-blocks --replication-factor 1 --partitions 1 --config "cleanup.policy=compact"
bin/kafka-topics.sh --create --zookeeper localhost:2181 --topic aion-transactions --replication-factor 1 --partitions 1 --config "cleanup.policy=compact"
