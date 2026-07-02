package com.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.bank.repository.AccountRepository;

@RestController
public class AccountController {
	
	@Autowired
	private AccountRepository accountRepository;
	
	
	
	

}
