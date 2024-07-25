package demo.camel.example.route;

import demo.camel.example.dto.TransactionDto;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.support.DefaultMessage;
import org.springframework.stereotype.Component;
import static demo.camel.example.constants.Constants.RABBIT_MQ_WIRETAP_SOURCE;
import static demo.camel.example.constants.Constants.AUDIT;
import static demo.camel.example.constants.Constants.AUDIT_TRANSACTION_ROUTE;
import static demo.camel.example.constants.Constants.RECEIVER;
import static demo.camel.example.constants.Constants.SENDER;
import static demo.camel.example.constants.Constants.TRANSFER_MESSAGE_1;
import static demo.camel.example.constants.Constants.TRANSFER_MESSAGE_2;
import java.util.Date;

import static org.apache.camel.LoggingLevel.INFO;

@Component
public class WireTapRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		fromF(RABBIT_MQ_WIRETAP_SOURCE, SENDER, SENDER).unmarshal().json(JsonLibrary.Jackson, TransactionDto.class)
				.wireTap(AUDIT_TRANSACTION_ROUTE).process(this::enrichTransactionDto).marshal()
				.json(JsonLibrary.Jackson, TransactionDto.class).toF(RABBIT_MQ_WIRETAP_SOURCE, RECEIVER, RECEIVER)
				.log(INFO, TRANSFER_MESSAGE_1);

		from(AUDIT_TRANSACTION_ROUTE).process(this::enrichTransactionDto).marshal() //direct to Audit Queue
				.json(JsonLibrary.Jackson, TransactionDto.class).log(INFO, TRANSFER_MESSAGE_2)
				.toF(RABBIT_MQ_WIRETAP_SOURCE, AUDIT, AUDIT);
	}

	private void enrichTransactionDto(Exchange exchange) {
		TransactionDto dto = exchange.getMessage().getBody(TransactionDto.class);
		dto.setTransactionDate(new Date().toString());

		Message message = new DefaultMessage(exchange);
		message.setBody(dto);
		exchange.setMessage(message);
	}
}
