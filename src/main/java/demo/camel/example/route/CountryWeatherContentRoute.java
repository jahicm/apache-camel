package demo.camel.example.route;

/**
 * Content Based Routing EIP , where message is quoted to different queue based on country code
 */
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.support.DefaultMessage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import demo.camel.example.dto.Weather_CountryDto;
import static org.apache.camel.LoggingLevel.INFO;
import static demo.camel.example.constants.Constants.RABBIT_MQ_WEATHER_COUNTRY_SOURCE;
import static demo.camel.example.constants.Constants.DIRECT_CH;
import static demo.camel.example.constants.Constants.DIRECT_DE;
import static demo.camel.example.constants.Constants.DIRECT_GENERAL;
import static demo.camel.example.constants.Constants.DE_Q;
import static demo.camel.example.constants.Constants.CH_Q;
import static demo.camel.example.constants.Constants.DEFAULT_Q;
import java.util.Date;

@Component
@ConditionalOnProperty(name = "demo.camel.example.country-weather", havingValue = "true") // in application.properties
public class CountryWeatherContentRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		from(RABBIT_MQ_WEATHER_COUNTRY_SOURCE).unmarshal().json(JsonLibrary.Jackson, Weather_CountryDto.class)
				.process(this::findCountryRoute).choice()
				.when(p -> p.getMessage().getBody(Weather_CountryDto.class).getCountry().equals("CH")).to(DIRECT_CH)
				.when(p -> p.getMessage().getBody(Weather_CountryDto.class).getCountry().equals("DE")).to(DIRECT_DE)
				.otherwise().to(DIRECT_GENERAL);

		from(DIRECT_CH).log(INFO, "Switzerland  ${body}").marshal().json(JsonLibrary.Jackson, Weather_CountryDto.class)
				.to(CH_Q);

		from(DIRECT_DE).log(INFO, "Germany ${body}").marshal().json(JsonLibrary.Jackson, Weather_CountryDto.class)
				.to(DE_Q);

		from(DIRECT_GENERAL).log(INFO, "Default Route").marshal().json(JsonLibrary.Jackson, Weather_CountryDto.class)
				.to(DEFAULT_Q);

	}

	private void findCountryRoute(Exchange exchange) {
		Weather_CountryDto dto = exchange.getMessage().getBody(Weather_CountryDto.class);
		dto.setReceivedTime(new Date().toString());

		Message message = new DefaultMessage(exchange);
		message.setBody(dto);
		exchange.setMessage(message);
	}
}
