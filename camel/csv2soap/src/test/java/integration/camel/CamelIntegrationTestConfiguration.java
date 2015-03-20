package integration.camel;

import org.apache.camel.RoutesBuilder;

public interface CamelIntegrationTestConfiguration {
	
	String propertyLocation();
	RoutesBuilder routes();
}
