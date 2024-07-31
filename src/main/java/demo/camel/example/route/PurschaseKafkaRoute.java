package demo.camel.example.route;
/**
 *  In just ferw lines of code we can integrate spring boot and Kafka using Camel integration framework.
 * In order to run this Camel-Kafka integration sample , it is required to set up apache-kafka project 
 * available on this Github. KStreamTwo will generate purchase data that will be pushed to purchases topic.
 * Only then this sample will start and read purchases topic from the Kafka, loaded by apache-kafka project.
 * 
 * The data will be enriched with the current date and pushed to purchases-audit topic. Alternatively to Rabbit-MQ 
 * and txt file purchases also.
 */
import static org.apache.camel.LoggingLevel.INFO;

import java.util.Date;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import static demo.camel.example.constants.Constants.AUDIT;
import static demo.camel.example.constants.Constants.KAFKA_ENDPOINT;
import static demo.camel.example.constants.Constants.PURCHASES;
import static demo.camel.example.constants.Constants.PURCHASES_AUDIT;
import static demo.camel.example.constants.Constants.RABBIT_MQ_WIRETAP_SOURCE;
import static demo.camel.example.constants.Constants.KAFKA_FILE;;

@Component
@ConditionalOnProperty(name = "demo.camel.example.kafka.enabled", havingValue = "true")
public class PurschaseKafkaRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		fromF(KAFKA_ENDPOINT, PURCHASES).log(INFO, "[${header.kafka.OFFSET}] [${body}]").bean(PurchaseEnricher.class)
				.toF(KAFKA_ENDPOINT, PURCHASES_AUDIT) //read from purchases Kafka topic into purchase-audit topic
				.toF(RABBIT_MQ_WIRETAP_SOURCE, AUDIT, AUDIT) // push the same data into Rabbit MQ called audit-transactions
				.to(KAFKA_FILE);// and finally dump the same ontent into the txt file called purchases.txt;
	}

	private class PurchaseEnricher {
		public String enrichPurchase(String purchase) {
			return purchase + "," + new Date();
		}
	}

}