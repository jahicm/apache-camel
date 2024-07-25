package demo.camel.example.route;

import static demo.camel.example.constants.Constants.AUDIT;
import static demo.camel.example.constants.Constants.COMPLEX_ROUTE;
import static demo.camel.example.constants.Constants.HEART_BEAT;
import static demo.camel.example.constants.Constants.RABBIT_MQ_WIRETAP_SOURCE;
import static demo.camel.example.constants.Constants.SEDA_MQ_FILE;
import static demo.camel.example.constants.Constants.SEDA_ROUTE;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.apache.camel.LoggingLevel.ERROR;

import java.util.Date;

import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.support.DefaultMessage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;;

/**
 * Example of Staged Event Driven Architecture (SEDA)
 */
@Component
@ConditionalOnProperty(name = "jss.camel.seda.enabled", havingValue = "true", matchIfMissing = true)
public class SedaRoute extends RouteBuilder {
	@Override
	public void configure() {
		from(HEART_BEAT).process(exchange -> {
			Message message = new DefaultMessage(exchange);
			message.setBody(new Date());
			exchange.setMessage(message);
		}).to(SEDA_ROUTE);

		from(SEDA_ROUTE).to(COMPLEX_ROUTE);

		from(COMPLEX_ROUTE).log(ERROR, "${body}").process(exchange -> SECONDS.sleep(2)).end()
				.convertBodyTo(String.class).to(SEDA_MQ_FILE)// dump it to the local file
				.toF(RABBIT_MQ_WIRETAP_SOURCE, AUDIT, AUDIT); // send it to Audit queue

	}
}