package net.awonline.microservices.oauth.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.CompositeFilter;

//@RestController
//@EnableOAuth2Client
///@EnableWebSecurity
//@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	// @Bean
	// @Override
	// public AuthenticationManager authenticationManager() throws Exception {
	// return super.authenticationManager();
	// }

	@Bean
	@Override
	public UserDetailsService userDetailsServiceBean() throws Exception {
		return super.userDetailsServiceBean();
	}

	// @Override
	// protected void configure(AuthenticationManagerBuilder auth) throws
	// Exception {
	// auth.inMemoryAuthentication().withUser("user").password("pass").roles("USER");
	// }

	// @Override
	// protected void configure(HttpSecurity http) throws Exception {
	// super.configure(http);
	// }

	// @Bean
	// public TokenStore tokenStore() {
	// return new InMemoryTokenStore();
	// }

	///////////////////
	@Autowired
	OAuth2ClientContext oauth2ClientContext;

	@Value("${security.oauth2.resource.userInfoUri}")
	String userInfoUri;

	@Value("${security.oauth2.client.clientId}")
	String clientId;

	@Autowired
	private OAuth2ClientContextFilter oAuth2ClientContextFilter;

	// @Autowired
	// private OAuth2ClientAuthenticationProcessingFilter
	// oAuth2ClientAuthenticationProcessingFilter;

	// @Override
	// protected void configure(HttpSecurity http) throws Exception {
//		// @formatter:off
//		 http .csrf().disable() 
//         .logout().logoutSuccessUrl("/").permitAll() //logout logic handled by spring
//         .and()
//         .antMatcher("/**")
//         .authorizeRequests()
//             .antMatchers("/", "/login/**", "/webjars/**", "/user")
//             .permitAll()
//         .anyRequest().authenticated();
////         .and().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint())
////         .and()
////         .addFilterBefore(ssoFilter(), OAuth2ClientAuthenticationProcessingFilter.class);
//			// @formatter:on
	// }

	private AuthenticationEntryPoint authenticationEntryPoint() {
		return new AuthenticationEntryPoint() {
			// You can use a lambda here
			@Override
			public void commence(HttpServletRequest aRequest, HttpServletResponse aResponse,
					AuthenticationException aAuthException) throws IOException, ServletException {
				aResponse.sendRedirect(
						"http://localhost:9001/oauth/token?redirect_uri=http://localhost:9002/prk/v1/posts/a");
			}
		};
	}

	@Bean
	public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(filter);
		registration.setOrder(-100);
		return registration;
	}

	protected Filter ssoFilter() {
		CompositeFilter filter = new CompositeFilter();
		List<Filter> filters = new ArrayList<>();
		filters.add(constructFilter("/login", google()));
		filter.setFilters(filters);
		return filter;
	}

	private Filter constructFilter(String endpoint, OAuth2ProtectedResourceDetails clientDetails) {
		OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter(endpoint);
		OAuth2RestTemplate template = new OAuth2RestTemplate(clientDetails, oauth2ClientContext);
		filter.setRestTemplate(template);
		filter.setTokenServices(new UserInfoTokenServices(userInfoUri, clientId));
		return filter;
	}

	// private Filter constructFilter(String endpoint,
	// OAuth2ProtectedResourceDetails clientDetails,
	// ResourceServerProperties resourceDetails) {
	// OAuth2ClientAuthenticationProcessingFilter filter = new
	// CustomOauth2AuthFilter(endpoint);
	// OAuth2RestTemplate template = new OAuth2RestTemplate(clientDetails,
	// oauth2ClientContext);
	// filter.setRestTemplate(template);
	// filter.setTokenServices(
	// new UserInfoTokenServices(resourceDetails.getUserInfoUri(),
	// clientDetails.getClientId()));
	// return filter;
	// }

	/*
	 * /Returns a new AuthorizationCodeResourceDetails object configured with
	 * the properties from the application.yml file
	 */
	@Bean
	@ConfigurationProperties("security.oauth2.client")
	public AuthorizationCodeResourceDetails google() {
		return new AuthorizationCodeResourceDetails();
	}

	/*
	 * /Returns a new ResourceServerProperties object configured with the
	 * properties from the application.yml file
	 */
	// @Bean
	// @ConfigurationProperties("google.resource")
	// ResourceServerProperties googleResource() {
	// return new ResourceServerProperties();
	// }

	/// ***///

	@Bean
	public RequestContextListener requestContextListener() {
		return new RequestContextListener();
	}

}
