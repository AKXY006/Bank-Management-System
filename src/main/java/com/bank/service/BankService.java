package com.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.repository.BankRepository;

@Service
public class BankService {
	
	@Autowired
	private BankRepository bankRepository;
	
	

}
