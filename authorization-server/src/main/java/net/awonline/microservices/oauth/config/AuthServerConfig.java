package net.awonline.microservices.oauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

	private static final String BLOG_CLIENT_ID = "blog-web-client";
	private static final String BLOG_SECRET = "blog-web-client-secret";
	private static final String BLOG_CLIENT_REDIRECT_URI = "https://localhost:8443/client";

	private static final String READONLY_BLOG_CLIENT_ID = "readonly-blog-web-client";
	private static final String READONLY_BLOG_SECRET = "readonly-blog-web-client-secret";
	private static final String READONLY_BLOG_CLIENT_REDIRECT_URI = "https://localhost:8444/another-client";

	private static final String[] AUTHORITIES = new String[] { "ROLE_USER" };
	private static final String[] AUTHORIZED_GRANT_TYPES = new String[] { "authorization_code", "refresh_token" };

	private static final String POST_RESOURCE_ID = "post-resource";
	private static final String COMMENT_RESOURCE_ID = "comment-resource";

	private static final String SCOPE_READ = "read";
	private static final String SCOPE_WRITE = "write";

	@Autowired
	TokenStore jwtTokenStore;

	@Autowired
	JwtAccessTokenConverter jwtAccessTokenConverter;

	@Autowired
	AuthenticationManager authenticationManager;

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		// @formatter:off
		endpoints
			.tokenStore(jwtTokenStore)
			.accessTokenConverter(jwtAccessTokenConverter)
			.authenticationManager(authenticationManager);
		// @formatter:on
	}

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
			 	.withClient(BLOG_CLIENT_ID).secret(BLOG_SECRET)
				.scopes(SCOPE_READ, SCOPE_WRITE)
				.resourceIds(POST_RESOURCE_ID, COMMENT_RESOURCE_ID)
			 	.redirectUris(BLOG_CLIENT_REDIRECT_URI)
				.authorities(AUTHORITIES)
				.authorizedGrantTypes(AUTHORIZED_GRANT_TYPES)
				.accessTokenValiditySeconds(300)
				.refreshTokenValiditySeconds(36000)
				.autoApprove(true)
			.and()
			 	.withClient(READONLY_BLOG_CLIENT_ID).secret(READONLY_BLOG_SECRET)
				.scopes(SCOPE_READ)
				.resourceIds(POST_RESOURCE_ID, COMMENT_RESOURCE_ID)
			 	.redirectUris(READONLY_BLOG_CLIENT_REDIRECT_URI)
				.authorities(AUTHORITIES)
				.authorizedGrantTypes(AUTHORIZED_GRANT_TYPES)
				.accessTokenValiditySeconds(300)
				.refreshTokenValiditySeconds(36000)
				.autoApprove(true);
		// @formatter:on
	}
}
