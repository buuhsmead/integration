package integration.camel.simpletimer;

import integration.camel.CamelIntegrationTestSupport;

import org.apache.camel.RoutesBuilder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleTimerTest extends CamelIntegrationTestSupport {

	Logger LOGGER = LoggerFactory.getLogger(SimpleTimerTest.class);

	public RoutesBuilder routes() {
		return new SimpleTimerRoute();
	}

	public String propertyLocation() {
		return "classpath:simpletimer.properties";
	}

	@Test
	public void testImportdata1() throws Exception {
		Thread.sleep(5000);
	}

}
