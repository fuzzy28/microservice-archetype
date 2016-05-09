#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import java.io.IOException;
import java.util.Properties;

import ${package}.exception.${domainName}IdNotConsistentException;
import ${package}.exception.${domainName}NotFoundException;
import ${package}.exception.${domainName}NotPersistedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Abstract Test class for Unit testing Controllers.
 * 
 * @author Joel F. Ruelos Jr.
 * @since 1.0
 */

@WebAppConfiguration
public class AbstractControllerTest {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    protected void setup() {
	mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    protected void setup(Object... controller) {
	mockMvc = MockMvcBuilders
		.standaloneSetup(controller)
		.setHandlerExceptionResolvers(
			mapException(
				${domainName}NotFoundException.class,
				HttpStatus.NOT_FOUND),
			mapException(
				${domainName}NotPersistedException.class,
				HttpStatus.BAD_REQUEST),
			mapException(
				${domainName}IdNotConsistentException.class,
				HttpStatus.BAD_REQUEST))
		.build();
    }

    protected String objectToJson(Object object) throws JsonProcessingException {
	return new ObjectMapper().writeValueAsString(object);
    }

    protected <T> T jsonToObject(String json, Class<T> clazz)
	    throws JsonParseException, JsonMappingException, IOException {
	return new ObjectMapper().readValue(json, clazz);
    }

    private SimpleMappingExceptionResolver mapException(Class<? extends Exception> clazz,
	    HttpStatus status) {

	SimpleMappingExceptionResolver result = new SimpleMappingExceptionResolver();

	// Setting customized exception mappings
	Properties p = new Properties();
	p.put(clazz.getName(), "Errors/Exception1");
	result.setExceptionMappings(p);
	result.setDefaultStatusCode(status.value());
	return result;
    }
}
