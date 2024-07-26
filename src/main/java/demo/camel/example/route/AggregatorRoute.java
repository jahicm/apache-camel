package demo.camel.example.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import demo.camel.example.util.MyAggregationStrategy;

import static demo.camel.example.constants.Constants.AUDIT;
import static demo.camel.example.constants.Constants.HEART_BEAT;
import static demo.camel.example.constants.Constants.RABBIT_MQ_WIRETAP_SOURCE;

import java.util.Date;
import java.util.Random;

@Component
public class AggregatorRoute extends RouteBuilder {
	final String CORRELATION_ID = "correlationId";

	@Override
	public void configure() throws Exception {

		Random random = new Random();

		from(HEART_BEAT).process(exchange -> {
			Message message = exchange.getMessage();
			message.setHeader(CORRELATION_ID, random.nextInt(4));
			message.setBody(new Date() + "");
		}).aggregate(header(CORRELATION_ID), new MyAggregationStrategy()).completionSize(5)
				.toF(RABBIT_MQ_WIRETAP_SOURCE, AUDIT, AUDIT)// send aggregated message to the audit queue
				.log(LoggingLevel.ERROR, "${header." + CORRELATION_ID + "} ${body}");

	}
}
