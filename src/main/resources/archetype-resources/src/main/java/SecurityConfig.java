#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Provides basic authentication to restful clients.
 * 
 * @author ${author}
 * @since 1.0
 */


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.inMemoryAuthentication()
			.withUser("admin").password("admin").roles("ADMIN")
			.and()
			.withUser("ems").password("ems").roles("EMS");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.
			csrf()
				.disable()
			.httpBasic()
			.and()
			.authorizeRequests()
				.antMatchers("/actuators/**").hasRole("ADMIN")
				.antMatchers("/departments/**").authenticated();
	}

}