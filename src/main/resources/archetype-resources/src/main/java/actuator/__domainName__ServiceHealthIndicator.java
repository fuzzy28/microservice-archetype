#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.actuator;

import ${package}.service.${domainName}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * The ${domainName}ServiceHealthIndicator class provides additional
 * information to /health actuator end point.
 * 
 * @author ${author}
 * @since 1.0
 */

@Component
public class ${domainName}ServiceHealthIndicator implements HealthIndicator {

    @Autowired
    private ${domainName}Service ${domainNameVariable}Service;

    @Value("${symbol_dollar}{health.indicator.message}")
    private String indicatorMessage;

    @Override
    public Health health() {

	long size = ${domainNameVariable}Service.countAll();
	boolean has${domainName} = size > 0;
	return (has${domainName} ? Health.up() : Health.down())
		.withDetail("info",
			String.format(indicatorMessage, has${domainName} ? size : "no"))
		.build();

    }

}