package net.awonline.microservices.oauth.blogclient.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableOAuth2Sso
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private OAuth2ProtectedResourceDetails oAuthResourceDetails;

	@Autowired
	private OAuth2ClientContext oAuth2ClientContext;

	@Bean
	public OAuth2RestOperations restTemplate() {
		return new OAuth2RestTemplate(oAuthResourceDetails, oAuth2ClientContext);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
			.csrf()
			.ignoringAntMatchers("/logout/**")
		.and()
			.antMatcher("/**").authorizeRequests()
			.antMatchers("/", "/login**").permitAll()
			.anyRequest().authenticated()
		.and()
			.logout()
			.deleteCookies("ANOTHERWEBCLIENTSESS")
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/");
		// @formatter:on
	}
}
