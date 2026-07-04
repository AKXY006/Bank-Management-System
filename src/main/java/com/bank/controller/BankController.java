package com.bank.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bank.entity.Bank;
import com.bank.repository.BankRepository;


@RestController
public class BankController {
	
	@Autowired
	private BankRepository bankRepository;
	
	@PostMapping("/bank")
	public String SaveBank(@RequestBody Bank bank) {
		bankRepository.save(bank);
		return "";
		
	}
}
