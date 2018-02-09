package net.awonline.microservices.oauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.web.context.request.RequestContextListener;

@Configuration
@EnableResourceServer
// @EnableOAuth2Client
// @EnableWebSecurity
// @EnableOAuth2Sso
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		// @formatter:off
//		http
//			.anonymous().disable()
//			.requestMatchers().antMatchers("/v1/posts/**")
//			.and().authorizeRequests()
//			.antMatchers("/v1/posts/**").access("hasRole('USER')");

			//.and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
		
		// hasRole('ADMIN') or hasRole('USER')
		
        http
//        .sessionManagement()
//		.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().
			.authorizeRequests().
			antMatchers("/").permitAll().
			antMatchers("/v1/posts/**").access("hasRole('USER')");
			//.and().addFilterBefore(ssoFilter(), OAuth2ClientAuthenticationProcessingFilter.class);
			//.and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
			
			//.authenticated();
		// @formatter:on
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId("blog-resource");
	}

	/////////////////////////////////////////////////

	@Autowired
	OAuth2ClientContext oauth2ClientContext;

	@Value("${security.oauth2.resource.userInfoUri}")
	String userInfoUri;

	@Value("${security.oauth2.client.clientId}")
	String clientId;

	@Bean
	public RequestContextListener requestContextListener() {
		return new RequestContextListener();
	}

	// @Override
	// protected void configure(HttpSecurity http) throws Exception {
	// // super.configure(http);
	// http.antMatcher("/**").addFilterBefore(ssoFilter(), Filter.class);
	// }

	// private Filter ssoFilter() {
	// OAuth2ClientAuthenticationProcessingFilter googleFilter = new
	// OAuth2ClientAuthenticationProcessingFilter(
	// "/login");
	// OAuth2RestTemplate googleTemplate = new OAuth2RestTemplate(google(),
	// oauth2ClientContext);
	// googleFilter.setRestTemplate(googleTemplate);
	// googleFilter.setTokenServices(new UserInfoTokenServices(userInfoUri,
	// clientId));
	// return googleFilter;
	// }
	//
	// @Bean
	// @ConfigurationProperties("security.oauth2.client")
	// public AuthorizationCodeResourceDetails google() {
	// return new AuthorizationCodeResourceDetails();
	// }

	// @Bean
	// @Primary
	// @ConfigurationProperties("security.oauth2.resource")
	// public ResourceServerProperties googleResource() {
	// return new ResourceServerProperties();
	// }

	// @Bean
	// public FilterRegistrationBean
	// oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
	// FilterRegistrationBean registration = new FilterRegistrationBean();
	// registration.setFilter(filter);
	// registration.setOrder(-100);
	// return registration;
	// }

	/////////////

	@Bean
	public OAuth2RestTemplate oauth2RestTemplate(OAuth2ClientContext oauth2ClientContext,
			OAuth2ProtectedResourceDetails details) {
		return new OAuth2RestTemplate(details, oauth2ClientContext);
	}
}
