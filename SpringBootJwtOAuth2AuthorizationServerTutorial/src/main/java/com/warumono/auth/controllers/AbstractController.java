package com.warumono.auth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.warumono.auth.services.AuthUserDetailsService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractController
{
	@Autowired
	protected AuthUserDetailsService userDetailsService;
}
