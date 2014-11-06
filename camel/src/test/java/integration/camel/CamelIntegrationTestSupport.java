package integration.camel;

import java.io.InputStream;
import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.camel.CamelContext;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.JndiRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CamelIntegrationTestSupport implements CamelIntegrationTestConfiguration {
	Logger log =  LoggerFactory.getLogger(CamelIntegrationTestSupport.class);
	
	CamelContext camelContext;
	
	public CamelIntegrationTestSupport(){
		try {
			camelContext = createCamelContext();
			camelContext.addRoutes(routes());
			PropertiesComponent pc = new PropertiesComponent();
			pc.setLocation(propertyLocation());
			camelContext.addComponent("properties", pc);
			camelContext.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	protected CamelContext createCamelContext() throws Exception {
        CamelContext context = new DefaultCamelContext(createRegistry());
        
        return context;
    }

    protected JndiRegistry createRegistry() throws Exception {
        return new JndiRegistry(createJndiContext());
    }

    protected Context createJndiContext() throws Exception {
        Properties properties = new Properties();

        // jndi.properties is optional
        InputStream in = getClass().getClassLoader().getResourceAsStream("jndi.properties");
        if (in != null) {
            log.debug("Using jndi.properties from classpath root");
            properties.load(in);
        } else {            
            properties.put("java.naming.factory.initial", "org.apache.camel.util.jndi.CamelInitialContextFactory");
        }
        return new InitialContext(new Hashtable<Object, Object>(properties));
    }
}
