#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Abstract Test which initialize the Spring boot application
 * 
 * @author Joel F. Ruelos Jr.
 * @since 1.0
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { Application.class, SecurityConfig.class })
@WebIntegrationTest
public abstract class AbstractIntegrationTest {

    @Test
    public void contextLoads() {
    }

}
