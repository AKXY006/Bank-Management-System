package com.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.bank.repository.BankRepository;

@RestController
public class BankController {
	
	@Autowired
	private BankRepository bankRepository;

}
