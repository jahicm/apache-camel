package demo.camel.example.route;

import demo.camel.example.dto.WeatherDto;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.support.DefaultMessage;
import org.springframework.stereotype.Component;

import java.util.Date;

import static org.apache.camel.LoggingLevel.ERROR;
import static demo.camel.example.constants.Constants.RABBIT_MQ_SOURCE;
import static demo.camel.example.constants.Constants.RABBIT_MQ_DESTINATION;
import static demo.camel.example.constants.Constants.RABBIT_MQ_FILE;

/*
 * Sample json message to be pushed to the queue
  {
	  "city":"London",
	  "temp":"23",
	  "unit":"C"
	}
*/
@Component
public class WeatherRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from(RABBIT_MQ_SOURCE).log(ERROR, "Before Enrichment: ${body}").unmarshal()
				.json(JsonLibrary.Jackson, WeatherDto.class).process(this::enrichWeatherDto)
				.log(ERROR, "After Enrichment: ${body}").marshal().json(JsonLibrary.Jackson, WeatherDto.class)
				.to(RABBIT_MQ_DESTINATION) // push to RabbitMQ queue
				.to(RABBIT_MQ_FILE);// Save to local file

	}

	private void enrichWeatherDto(Exchange exchange) {
		WeatherDto dto = exchange.getMessage().getBody(WeatherDto.class);
		dto.setReceivedTime(new Date().toString());

		Message message = new DefaultMessage(exchange);
		message.setBody(dto);
		exchange.setMessage(message);
	}
}
