package com.warumono.auth.services;

import java.util.Collection;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.google.common.collect.Sets;
import com.warumono.auth.entities.Role;
import com.warumono.auth.entities.User;
import com.warumono.auth.enums.Authority;
import com.warumono.auth.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthUserDetailsService implements UserDetailsService
{
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		log.debug("========== loadUserByUsername ==========");
		log.debug("username : {}", username);
		log.debug("========== loadUserByUsername ==========");
		
		User user = userRepository.findByUsername(username);
		
		if(Objects.isNull(user))
		{
			throw new UsernameNotFoundException(username);
		}
		
		return org.springframework.security.core.userdetails.User.withUsername(username).password(user.getPassword()).roles(user.roles()).build();
	}
	
	// -----
	
	public User store(String username, String password, Authority authority)
	{
		Collection<Role> _roles = Authority.roles(authority);
		
		User user = User.builder()
				.username(username)
				.password(password)
				.build();
		
		_roles.forEach(r -> { user.bind(r); });
		
		return userRepository.save(user);
	}
}
