package com.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.repository.AccountRepository;

@Service
public class AccountServiceImp {
	
	@Autowired
	private AccountRepository accountRepository;
	
	

}
