package com.warumono.auth.configurations;

import java.security.KeyPair;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import com.warumono.auth.services.AuthUserDetailsService;

import lombok.extern.slf4j.Slf4j;

@EnableAuthorizationServer
@Configuration
@Slf4j
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter
{
	@Value("${security.oauth2.resource.id}")
	private String RESOURCE_ID;
	
	@Value("${security.oauth2.client.access-token-validity-seconds}")
	private Integer ACCESS_TOKEN_VALIDITY_SECONDS;
	
	@Value("${security.oauth2.keystore.jks}")
	private String KEYSTORE;
	
	@Value("${security.oauth2.keystore.storepass}")
	private String KEYSTORE_STOREPASS;
	
	@Value("${security.oauth2.keystore.key-pair}")
	private String KEYSTORE_KEY_PAIR;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private AuthUserDetailsService userDetailsService;
	
	@Autowired
    private DataSource dataSource;
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter()
	{
		KeyPair keyPair = new KeyStoreKeyFactory
		(
			new ClassPathResource(KEYSTORE), 
			KEYSTORE_STOREPASS.toCharArray()
		).getKeyPair(KEYSTORE_KEY_PAIR);

		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		jwtAccessTokenConverter.setKeyPair(keyPair);
		
		return jwtAccessTokenConverter;
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception
	{
		security
			//// will be applied to     /oauth/token_key
			.tokenKeyAccess("isAnonymous() || hasRole('ROLE_TRUSTED_CLIENT')") // permitAll()
			//// will be application to /oauth/check_token
			.checkTokenAccess("hasRole('TRUSTED_CLIENT')") // isAuthenticated()
			.allowFormAuthenticationForClients();
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception
	{
		endpoints
			// Which authenticationManager should be used for the password grant
			// If not provided, ResourceOwnerPasswordTokenGranter is not configured
			.authenticationManager(authenticationManager)
			
//			.userDetailsService(userDetailsService)
			
			// Use JwtTokenStore and our jwtAccessTokenConverter
			.accessTokenConverter(jwtAccessTokenConverter());
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception
	{
		// @formatter:off
		//*
		clients
			.jdbc(dataSource)
			.passwordEncoder(passwordEncoder());
		/*/
		final String READ = "read";
		final String[] READ_WRITE = { READ, "write" };
		final String[] READ_WRITE_TRUST = { READ, "write", "trust" };
		final String REDIRECT_URI = "http://localhost:8080/auth/tutorial";
			
		clients
			.inMemory()
				// Confidential client where client secret can be kept safe (e.g. server side)
				.withClient("confidential").secret("secret")
				.authorizedGrantTypes("client_credentials", "authorization_code", "refresh_token")
				.scopes(READ_WRITE)
				.redirectUris(REDIRECT_URI)
				.resourceIds(RESOURCE_ID)

				.and()

				// Public client where client secret is vulnerable (e.g. mobile apps, browsers)
				.withClient("public") // No secret!
				.authorizedGrantTypes("implicit")
				.scopes(READ)
				.redirectUris(REDIRECT_URI)
				.resourceIds(RESOURCE_ID)
				
				.and()
				
				// Trusted client: similar to confidential client but also allowed to handle user password
				.withClient("trusted").secret("secret")
				.authorities("ROLE_TRUSTED_CLIENT")
				.authorizedGrantTypes("client_credentials", "password", "authorization_code", "refresh_token")
				.scopes(READ_WRITE_TRUST)
				.redirectUris(REDIRECT_URI)
				.resourceIds(RESOURCE_ID)

				.and()

				// Trusted client: similar to confidential client but also allowed to handle user password
				.withClient("tutorial").secret("secret")
				.authorities("ROLE_TRUSTED_CLIENT", "USER")
				.authorizedGrantTypes("client_credentials", "password", "authorization_code", "refresh_token")
				.scopes(READ_WRITE)
				.redirectUris(REDIRECT_URI)
				.resourceIds(RESOURCE_ID)
				.autoApprove(Boolean.TRUE).scopes(READ_WRITE);
		//*/
		// @formatter:on
	}
}
