package com.warumono.auth.controllers.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.warumono.auth.enums.Authority;

@RequestMapping(value = "/user")
public interface UserControllerInterface
{
	@PostMapping(value = "{authority}")
	ResponseEntity<Object> register
	(
		@PathVariable(value = "authority") Authority authority, 
		@RequestParam(value = "username", required = true) String username, 
		@RequestParam(value = "password", required = false) String password
	);
}
