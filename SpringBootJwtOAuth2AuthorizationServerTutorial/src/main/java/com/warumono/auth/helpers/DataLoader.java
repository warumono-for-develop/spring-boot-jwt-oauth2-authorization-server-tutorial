package com.warumono.auth.helpers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.google.common.collect.Sets;
import com.warumono.auth.entities.OAuthClientDetails;
import com.warumono.auth.entities.Role;
import com.warumono.auth.entities.User;
import com.warumono.auth.repositories.OAuth2CleintDetailsRepository;
import com.warumono.auth.repositories.RoleRepository;
import com.warumono.auth.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DataLoader implements CommandLineRunner
{
	@Autowired
	private OAuth2CleintDetailsRepository oAuth2CleintDetailsRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void run(String... args) throws Exception
	{
		log.debug("Data loading . . .");
		
		log.debug("oAuth2CleintDetailsRepository : {}", oAuth2CleintDetailsRepository);
		
		final Long ONE_MINUTE = Long.valueOf(1);
		final Long ONE_HOUR = ONE_MINUTE * 60;
		final String REDIRECT_URI = "http://localhost:8080/auth/tutorial";
		
		try
		{
			Collection<OAuthClientDetails> clients = Sets.newHashSet();
			
//			OAuth2ClientDetails.of(client_id, resource_ids, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove)
//			OAuth2ClientDetails.on(client_id, client_secret, scope, authorized_grant_types, authorities, access_token_validity, additional_information)
			OAuthClientDetails aClient = OAuthClientDetails.builder()
					.resource_ids("your-resource-id")
					.client_id("client")
					.client_secret("secret")
					.scope("read,trust")
					.authorized_grant_types("client_credentials,password,authorization_code,refresh_token")
					.web_server_redirect_uri(REDIRECT_URI)
					.authorities("ROLE_USER")
					.access_token_validity(ONE_HOUR * 6)
					.refresh_token_validity(ONE_HOUR * 6)
//					.additional_information(additional_information)
					.autoapprove("read,trust")
					.build();
			
			OAuthClientDetails bClient = OAuthClientDetails.builder()
					.resource_ids("admin-resource-id")
					.client_id("admin")
					.client_secret("secret")
					.scope("read,write,trust")
					.authorized_grant_types("password,refresh_token")
					.web_server_redirect_uri(REDIRECT_URI)
					.authorities("ROLE_USER,ROLE_ADMIN")
					.access_token_validity(ONE_HOUR * 60)
					.refresh_token_validity(ONE_HOUR * 60)
//					.additional_information(additional_information)
					.autoapprove("trust")
					.build();
			
			log.debug("aClient : {}", aClient);
			log.debug("bClient : {}", bClient);
			
			clients.add(aClient);
			clients.add(bClient);
			
			Role userRole = Role.builder().name("ROLE_USER").build();
			Role staffRole = Role.builder().name("ROLE_STAFF").build();
			Role adminRole = Role.builder().name("ROLE_ADMIN").build();
			
			Collection<User> users = Sets.newHashSet();
			
//			User.of(username, password, enabled)
			User aUser = User.builder()
					.username("auser")
					.password("apassword")
					.enabled(Boolean.TRUE)
					.build();
			
			User bUser = User.builder()
					.username("buser")
					.password("bpassword")
					.enabled(Boolean.FALSE)
					.build();
			
			User cUser = User.builder()
					.username("cuser")
					.password("cpassword")
					.enabled(Boolean.TRUE)
					.build();
			
			aUser.bind(userRole);
			bUser.bind(staffRole);
			cUser.bind(adminRole);
			
			users.add(aUser);
			users.add(bUser);
			users.add(cUser);
			
			try
			{
				oAuth2CleintDetailsRepository.save(clients);
				
//				roleRepository.save(roles);
				
				userRepository.save(users);
			}
			catch(DataIntegrityViolationException ignore)
			{
				ignore.printStackTrace();
				
				log.error("{}", ignore.getMessage());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
			log.error("{}", e.getMessage());
		}
	}
}
