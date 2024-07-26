package demo.camel.example.constants;

public class Constants {

	public static final String RABBIT_MQ_SOURCE = "spring-rabbitmq:amq.direct?routingKey=weather&queues=weather";
	public static final String RABBIT_MQ_DESTINATION = "spring-rabbitmq:amq.direct?routingKey=weatherEvents&queues=weatherEvents";
	public static final String RABBIT_MQ_FILE = "file:///C:/Users/Administrator?fileName=weather-events.txt&fileExist=Append";
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
}