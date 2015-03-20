package integration.camel.simpletimer;

import org.apache.camel.builder.RouteBuilder;

public class SimpleTimerRoute extends RouteBuilder {


	@Override
	public void configure() throws Exception {
		from("timer:SimpleTimer?period={{simpletimer.period}}").routeId("SimpleTimerRoute").log("Greetings!");
	}
}
