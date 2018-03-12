package com.warumono.auth.configurations;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import lombok.extern.slf4j.Slf4j;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
@Slf4j
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
	@Value("${security.auth.query.users-by-username}")
	private String USERS_BY_USERNAME_QUERY;
	
	@Value("${security.auth.query.authorities-by-username}")
	private String AUTHORITIES_BY_USERNAME_QUERY;
	
	@Autowired
    private DataSource dataSource;
	
	@Autowired
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth
			//*
			.jdbcAuthentication()
				.dataSource(dataSource)
					.usersByUsernameQuery(USERS_BY_USERNAME_QUERY)
					.authoritiesByUsernameQuery(AUTHORITIES_BY_USERNAME_QUERY);
			
//			.userDetailsService(userDetailsService);
			/*/
			.inMemoryAuthentication()
				.withUser("admin").password("password").roles("USER", "ADMIN")
				.and()
				.withUser("user").password("password").roles("USER")
				.and()
				.withUser("tutorial").password("password").roles("USER");
			//*/
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http
			.formLogin()
			.and()
			.httpBasic().disable()
			.anonymous().disable()
			.authorizeRequests()
				.anyRequest().authenticated();
	}
}
