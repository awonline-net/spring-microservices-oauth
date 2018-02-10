package net.awonline.microservices.oauth.postservice.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@EnableWebSecurity
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		// @formatter:off
        http
        	.anonymous().disable()
	        .authorizeRequests().anyRequest().authenticated();
     // @formatter:on
	}

	// TODO configure resourceId
	// @Override
	// public void configure(ResourceServerSecurityConfigurer resources) throws
	// super.configure(resources);
	// }
}
