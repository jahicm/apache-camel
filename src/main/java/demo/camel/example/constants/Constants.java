package demo.camel.example.constants;

public class Constants {

	public static final String RABBIT_MQ_SOURCE = "spring-rabbitmq:amq.direct?routingKey=weather&queues=weather";
	public static final String RABBIT_MQ_DESTINATION = "spring-rabbitmq:amq.direct?routingKey=weatherEvents&queues=weatherEvents";
	public static final String RABBIT_MQ_FILE = "file:///C:/Users/Administrator?fileName=weather-events.txt&fileExist=Append";
	public static final String RABBIT_MQ_MULTICAST_FILE_1 = "file:///C:/Users/Administrator?fileName=multicast1.txt&fileExist=Append";
	public static final String RABBIT_MQ_MULTICAST_FILE_2 = "file:///C:/Users/Administrator?fileName=multicast2.txt&fileExist=Append";
	public static final String SPLITTER_SOURCE_FILE = "file://src/main/resources/xml?noop=true";
	public static final String SEDA_MQ_FILE = "file:///C:/Users/Administrator?fileName=seda.txt&fileExist=Append";
	public static final String RABBIT_MQ_WIRETAP_SOURCE = "spring-rabbitmq:amq.direct?queues=%s&routingKey=%s";
	public static final String SENDER = "sender";
	public static final String RECEIVER = "receiver";
	public static final String AUDIT_TRANSACTION_ROUTE = "direct:audit-transaction";
	public static final String AUDIT = "audit-transactions";
	public static final String TRANSFER_MESSAGE_1 = "Money Transferred: ${body}";
	public static final String TRANSFER_MESSAGE_2 = "Message wire-tapped to the audit queue";
	public static final String HEART_BEAT = "timer:ping?period=200";
	public static final String SEDA_ROUTE = "seda:weightLifter?multipleConsumers=true";
	public static final String COMPLEX_ROUTE="direct:complexProcess";
	public static final String SOURCE_FOLDER =  "src/main/resources/xml";
	public static final String SPLITTER_HIGH_VALUE_CUSTOMER="spring-rabbitmq:amq.direct?routingKey=splitter-route-high-queue&queues=high-queue";
	public static final String SPLITTER_MEDIUM_VALUE_CUSTOMER="spring-rabbitmq:amq.direct?routingKey=splitter-route-medium-queue&queues=medium-queue";
	public static final String SPLITTER_LOW_VALUE_CUSTOMER="spring-rabbitmq:amq.direct?routingKey=splitter-route-low-queue&queues=low-queue";
	public static final String MULTICAST_ROUTE_TX_1="direct:transaction1";
	public static final String MULTICAST_ROUTE_TX_2="direct:transaction2";
	public static final String RABBIT_MQ_WEATHER_COUNTRY_SOURCE = "spring-rabbitmq:amq.direct?routingKey=weather-country&queues=weather-country";
	public static final String DIRECT_DE="direct:DE";
	public static final String DIRECT_CH="direct:CH";
	public static final String DIRECT_GENERAL="direct:GENERAL";
	public static final String CH_Q="spring-rabbitmq:amq.direct?routingKey=ch-queue&queues=ch-queue";
	public static final String DE_Q="spring-rabbitmq:amq.direct?routingKey=de-queue&queues=de-queue";
	public static final String DEFAULT_Q="spring-rabbitmq:amq.direct?routingKey=default-queue&queues=default-queue";
	public static final String KAFKA_ENDPOINT = "kafka:%s?brokers=localhost:9092&autoOffsetReset=earliest";
	public static final String PURCHASES="purchases";
	public static final String PURCHASES_AUDIT="purchase-audit";
	public static final String KAFKA_FILE = "file:///C:/Users/Administrator?fileName=purchases.txt&fileExist=Append";
}