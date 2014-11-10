package integration.camel;

import integration.camel.csv2soap.Csv2SoapRoute;

import java.io.File;
import java.io.IOException;

import org.apache.camel.RoutesBuilder;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleCamelTest extends CamelIntegrationTestSupport {

	Logger LOGGER = LoggerFactory.getLogger(SimpleCamelTest.class);

	public RoutesBuilder routes() {
		return new Csv2SoapRoute();
	}

	public String propertyLocation() {
		return "classpath:camel.properties";
	}

	//@Test
	public void testImportdata1() throws Exception {
		//importCSV("TABLE-2002-05-30T09:00:00.CSV");
		//Thread.sleep(50000);
	}

	private void importCSV(String string) {
		try {
			FileUtils.copyFile(new File("src/test/resources/" + string),
					new File("target/import/" + string));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
