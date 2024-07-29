package demo.camel.example.route;

/**
 * Multi-cast EIP sends to multiple predefined routes same message in parallel.
 */
import static org.apache.camel.LoggingLevel.INFO;
import static demo.camel.example.constants.Constants.HEART_BEAT;
import static demo.camel.example.constants.Constants.RABBIT_MQ_MULTICAST_FILE_1;
import static demo.camel.example.constants.Constants.RABBIT_MQ_MULTICAST_FILE_2;
import static demo.camel.example.constants.Constants.MULTICAST_ROUTE_TX_1;
import static demo.camel.example.constants.Constants.MULTICAST_ROUTE_TX_2;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "demo.camel.example.route", havingValue = "true")
public class MulticastRoute extends RouteBuilder {
	@Override
	public void configure() throws Exception {

		AtomicInteger orderId = new AtomicInteger(100);

		from(HEART_BEAT)
				.process(exchange -> exchange.getIn()
						.setBody("{transaction-id: '" + (orderId.getAndIncrement()) + "', " + "price: 'CHF20.00'}"))
				.multicast().parallelProcessing().to(MULTICAST_ROUTE_TX_1, MULTICAST_ROUTE_TX_2);

		from(MULTICAST_ROUTE_TX_1).process(exchange -> enrich(exchange, "TX_1_PROCESSED")).log(INFO, "${body}")
				.toF(RABBIT_MQ_MULTICAST_FILE_1).log(INFO, " TX1 saved to file");

		from(MULTICAST_ROUTE_TX_2).process(exchange -> enrich(exchange, "TX_2_PROCESSED")).log(INFO, "${body}")
				.to(RABBIT_MQ_MULTICAST_FILE_2).log(INFO, " TX2 saved to file");
	}

	private void enrich(Exchange exchange, String statusValue) {
		Message in = exchange.getIn();
		String order = in.getBody(String.class);
		String status = "'TX status': '" + statusValue + "'";
		String body = order.replace("}", ", " + status + "}");
		in.setBody(body);
	}
}
