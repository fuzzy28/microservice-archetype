#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hazelcast.config.SecurityConfig;

/**
 * Abstract Test which initialize the Spring boot application
 * 
 * @author Joel F. Ruelos Jr.
 * @since 1.0
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { Application.class, SecurityConfig.class })
public abstract class AbstractTest {

    @Test
    public void contextLoads() {
    }

}
