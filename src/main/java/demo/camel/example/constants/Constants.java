package demo.camel.example.constants;

public class Constants {

	public static final String RABBIT_MQ_SOURCE="spring-rabbitmq:amq.direct?routingKey=weather&queues=weather";
	public static final String RABBIT_MQ_DESTINATION="spring-rabbitmq:amq.direct?routingKey=weatherEvents&queues=weatherEvents";
	public static final String RABBIT_MQ_FILE="file:///C:/Users/Administrator?fileName=weather-events.txt&fileExist=Append";
}
