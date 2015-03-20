package integration.camel.csv2soap;

import org.apache.camel.CamelContext;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.main.Main;

public class CSV2SOAP extends Main {

	public static void main(String... args) throws Exception {
		CSV2SOAP csv2soap = new CSV2SOAP();
		csv2soap.run(args);
	}

	@Override
	protected CamelContext createContext() {
		DefaultCamelContext defaultCamelContext = new DefaultCamelContext();
		PropertiesComponent pc = new PropertiesComponent();
		pc.setLocation("file:csv2soap.properties");
		defaultCamelContext.addComponent("properties", pc);

		try {
			defaultCamelContext.addRoutes(new Csv2SoapRoute());
		} catch (Exception e) {
			throw new RuntimeException("Error starting route.", e);
		}
		return defaultCamelContext;
	}
}