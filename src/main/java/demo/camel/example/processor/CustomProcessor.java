package demo.camel.example.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class CustomProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		String bodyAsString = exchange.getIn().getBody(String.class);
		System.out.println("******Custome Processor:" + bodyAsString + "****************");
	}

}
