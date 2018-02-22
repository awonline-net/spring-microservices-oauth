package net.awonline.microservices.oauth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
	        .permitAll()
	    .and()
	    	.cors()
	    	.configurationSource(logoutCorsConfigurationSource())
	    .and()
			.logout()
			.deleteCookies("AUTHSESS");
        
     // @formatter:on
	}

	private CorsConfigurationSource logoutCorsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedOrigin("*");
		config.setAllowCredentials(true);
		config.addAllowedHeader("X-Requested-With");
		config.addAllowedHeader("Content-Type");
		config.addAllowedMethod(HttpMethod.POST);
		source.registerCorsConfiguration("/logout", config);
		return source;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// @formatter:off
        auth.inMemoryAuthentication()
            .withUser("user").password("pass").roles("USER");
        // @formatter:on
	}
}
