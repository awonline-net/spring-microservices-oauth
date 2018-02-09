package net.awonline.microservices.oauth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

	private static final String CLIENT_ID = "blog-web-client";
	private static final String SECRET = "blog-web-client-secret";
	private static final String REDIRECT_URIS[] = new String[] { "http://localhost:8080/client",
			"http://localhost:8081/another-client" };

	private static final String[] AUTHORITIES = new String[] { "ROLE_USER" };
	private static final String[] SCOPES = new String[] { "web-client" };
	private static final String[] AUTHORIZED_GRANT_TYPES = new String[] { "authorization_code", "refresh_token" };

	@Override
	public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		// @formatter:off
		oauthServer
			.tokenKeyAccess("permitAll()")
			.checkTokenAccess("isAuthenticated()");
		// @formatter:on
	}

	@Override
	public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
		// @formatter:off
		 clients
		 	.inMemory()
		 	.withClient(CLIENT_ID).secret(SECRET)
			.scopes(SCOPES)
		 	.redirectUris(REDIRECT_URIS)
			.authorities(AUTHORITIES)
			.authorizedGrantTypes(AUTHORIZED_GRANT_TYPES)
			.accessTokenValiditySeconds(300)
			.refreshTokenValiditySeconds(36000)
			.autoApprove(true);
		// @formatter:on
	}
}
