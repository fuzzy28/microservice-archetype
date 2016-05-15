#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.hateoas.config.EnableEntityLinks;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
/**
 * Application Startup Class. It serves as both the runtime application entry
 * point and the central Java configuration class.
 * 
 * @author ${author}
 * @since 1.0
 */

@SpringBootApplication
@EnableTransactionManagement
@EnableAspectJAutoProxy
@EnableCaching(order = 2)
@EnableEntityLinks
@EnableWebSecurity
public class Application {

    public static void main(String[] args) {
	SpringApplication.run(Application.class, args);
    }

    @Bean
    public CacheManager cacheManager() {
	GuavaCacheManager cacheManager = new GuavaCacheManager("${domainName.toLowerCase()}s");
	return cacheManager;
    }

    @Bean
    public MessageSource messageSource() {
	ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
	messageBundle.setBasename("classpath:messages/messages");
	messageBundle.setDefaultEncoding("UTF-8");
	return messageBundle;
    }
}