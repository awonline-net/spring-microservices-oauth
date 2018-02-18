package net.awonline.microservices.oauth.commentservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;

@EnableWebSecurity
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	@Autowired
	DefaultTokenServices jwtTokenServices;

	@Override
	public void configure(ResourceServerSecurityConfigurer config) {
		// @formatter:off
		config
			.tokenServices(jwtTokenServices)
			.resourceId("comment-resource");
		// @formatter:on
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		// @formatter:off
        http
        	.anonymous().disable()
	        .authorizeRequests().anyRequest().authenticated();
     // @formatter:on
	}
}
