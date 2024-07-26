package demo.camel.example.route;
/**
 * 
 * Splitter EIP , where XML is loaded from the file with customers and Splitter splits 
 * XML  based on type of customer( H,L,M). Finally each type sent to its queue.
 */
import static demo.camel.example.constants.Constants.SPLITTER_HIGH_VALUE_CUSTOMER;
import static demo.camel.example.constants.Constants.SPLITTER_LOW_VALUE_CUSTOMER;
import static demo.camel.example.constants.Constants.SPLITTER_MEDIUM_VALUE_CUSTOMER;
import static demo.camel.example.constants.Constants.SPLITTER_SOURCE_FILE;
import static org.apache.camel.LoggingLevel.INFO;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class SplitterRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		from(SPLITTER_SOURCE_FILE).split(xpath("/customers/customer")) // Split each Customer node
				.choice().when(xpath("/customer/type = 'H'")).to(SPLITTER_HIGH_VALUE_CUSTOMER) // Send to high-queue
				.log(INFO, "High value customer:${body}").when(xpath("/customer/type = 'M'"))
				.to(SPLITTER_MEDIUM_VALUE_CUSTOMER) // Send to medium-queue
				.log(INFO, "Medium value customer:${body}") // medium-queue
				.when(xpath("/customer/type = 'L'")).to(SPLITTER_LOW_VALUE_CUSTOMER) // Send to low-queue
				.log(INFO, "Low value customer:${body}") // low-queue
				.endChoice().end();
	}
}
