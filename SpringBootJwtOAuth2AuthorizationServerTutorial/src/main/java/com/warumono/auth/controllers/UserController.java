package com.warumono.auth.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.warumono.auth.controllers.interfaces.UserControllerInterface;
import com.warumono.auth.entities.User;
import com.warumono.auth.enums.Authority;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class UserController extends AbstractController implements UserControllerInterface
{
	@Override
	public ResponseEntity<Object> register(@PathVariable Authority authority, String username, String password)
	{
		log.debug("=========== auth");
		log.debug("authority : {}", authority);
		log.debug("username : {}", username);
		log.debug("password : {}", password);
		
		User user = userDetailsService.store(username, password, authority);
		
		return ResponseEntity.ok(user);
	}
}

