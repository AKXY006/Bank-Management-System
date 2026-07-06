package com.bank.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.entity.Account;
import com.bank.entity.AccountType;
import com.bank.service.AccountService;
import com.bank.util.ResponseStructure;


@RestController
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@PostMapping("/{bankId}")
	public ResponseEntity<ResponseStructure<Account>> saveAccount( @RequestBody Account account, @PathVariable Integer bankId){
		return accountService.saveAccount(account,bankId);
		
	}
	
	@GetMapping
	public ResponseEntity<ResponseStructure<List<Account>>> findAllAccount(){
		return accountService.findAllAccounts();
	}
	
	@GetMapping("/{accountId}")
	public ResponseEntity<ResponseStructure<Account>> findById(@PathVariable Integer accountId){
		return accountService.findById(accountId);
	}
	
	@DeleteMapping("/{accountId}")
	public ResponseEntity<ResponseStructure<Account>> deleteById(@PathVariable Integer accountId){
		return accountService.deleteById(accountId);
	}
	
	@GetMapping("/bankid/{bankId}")
	public ResponseEntity<ResponseStructure<List<Account>>> findByBankId(@PathVariable Integer bankId){
		return accountService.findByBankId(bankId);
	}
	
	
	@GetMapping("/type/{accountType}")
	public ResponseEntity<ResponseStructure<List<Account>>> findByAccountType(@PathVariable AccountType accountType) {
	    return accountService.findByAccountType(accountType);
	}
	
	
	

	
	
	
	

}
