package net.awonline.microservices.oauth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@Configuration
@EnableWebSecurity
@EnableResourceServer
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
        http
        	.requestMatchers()
            .antMatchers("/login", "/oauth/authorize")
        .and()
	        .authorizeRequests()
	        .anyRequest()
	        .authenticated()
        .and()
	        .formLogin()
	        .permitAll();
     // @formatter:on
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// @formatter:off
        auth.inMemoryAuthentication()
            .withUser("user").password("pass").roles("USER");
        // @formatter:on
	}
}
