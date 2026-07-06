package com.bank.service;

import java.util.List;
import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bank.entity.Account;
import com.bank.entity.AccountType;
import com.bank.entity.Bank;
import com.bank.exception.AccountNotFoundException;
import com.bank.exception.AccountNumberAlreadyExitException;
import com.bank.exception.BankNotFoundException;
import com.bank.exception.IdNotFoundException;
import com.bank.repository.AccountRepository;
import com.bank.repository.BankRepository;
import com.bank.util.ResponseStructure;


@Service
public class AccountService{
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private BankRepository bankRepository;
	
	public ResponseEntity<ResponseStructure<Account>>  saveAccount(Account account , Integer bankId){
		
		Optional<Bank> optionalBank = bankRepository.findById(bankId);
		
		if(optionalBank.isEmpty()) {
			throw new BankNotFoundException("Bank does not exist");
		}
		
		Optional<Account> optionalAccount = accountRepository.findByAccountNumber(account.getAccountNumber() );
		
		if(optionalAccount.isPresent()) {
			throw new AccountNumberAlreadyExitException("Account number already Exist");
		}
		
		 account.setBank(optionalBank.get());
		 
		Account saveAccount = accountRepository.save(account);
		
		ResponseStructure<Account> responseStructure = new ResponseStructure<>();
		responseStructure.setStatusCode(HttpStatus.CREATED.value());
		responseStructure.setMessage("Account Create successful");
		responseStructure.setData(saveAccount);
		
		return new ResponseEntity<>(responseStructure,HttpStatus.CREATED);
		
	
	}
	
	
	public ResponseEntity<ResponseStructure<List<Account>>>  findAllAccounts(){
		List<Account> optional = accountRepository.findAll();
		
		if(optional.isEmpty()) {
			throw new AccountNotFoundException("Account Not Found");
	}
		
		ResponseStructure<List<Account>> responseStructure = new ResponseStructure<>();
	    responseStructure.setStatusCode(HttpStatus.OK.value());
	    responseStructure.setMessage("All Accounts Found Successfully");
	    responseStructure.setData(optional);

	    return new ResponseEntity<>(responseStructure, HttpStatus.OK);
	}
	
	public ResponseEntity<ResponseStructure<Account>> findById(Integer accountId){
		Optional<Account> optional = accountRepository.findById(accountId);
		
		if(optional.isEmpty()) {
			throw new IdNotFoundException("Id Not Found");
		}
		
		ResponseStructure<Account> responseStructure = new ResponseStructure<>();
		 responseStructure.setStatusCode(HttpStatus.OK.value());
		    responseStructure.setMessage("All Accounts Found Successfully");
		    responseStructure.setData(optional.get());

		    return new ResponseEntity<>(responseStructure, HttpStatus.OK);
		
	}
	
	
	public ResponseEntity<ResponseStructure<Account>> deleteById(Integer accountId){
		Optional<Account> optional = accountRepository.findById(accountId);
		
		if(optional.isEmpty()) {
			throw new IdNotFoundException("Id Not Found");
		}
		
		ResponseStructure<Account> responseStructure = new ResponseStructure<>();
		 responseStructure.setStatusCode(HttpStatus.OK.value());
		    responseStructure.setMessage("Accounts delete Successfully");
		    responseStructure.setData(optional.get());

		    return new ResponseEntity<>(responseStructure, HttpStatus.OK);
	}
	
	public ResponseEntity<ResponseStructure<List<Account>>> findByBankId(Integer bankId){
		Optional<Bank> optional = bankRepository.findById(bankId);
		
		if(optional.isEmpty()) {
			throw new IdNotFoundException("Id Not Found");
		}
		
		List<Account> accounts = optional.get().getAccounts();
		
		ResponseStructure<List<Account>> responseStructure = new ResponseStructure<>();
		 responseStructure.setStatusCode(HttpStatus.OK.value());
		    responseStructure.setMessage("Account found Successfully");
		    responseStructure.setData(accounts);

		    return new ResponseEntity<>(responseStructure, HttpStatus.OK);
	}
	
	public ResponseEntity<ResponseStructure<List<Account>>> findByAccountType(AccountType accountType) {
		List<Account> accounts = accountRepository.findByAccountType(accountType);

		 if (accounts.isEmpty()) {
			 throw new AccountNotFoundException("Account Type Not Found");
		 }
		 
		 ResponseStructure<List<Account>> responseStructure = new ResponseStructure<>();
		  responseStructure.setStatusCode(HttpStatus.OK.value());
		    responseStructure.setMessage("Accounts Found Successfully");
		    responseStructure.setData(accounts);
		    
		    return new ResponseEntity<>(responseStructure,HttpStatus.OK);
	}
	

	


}
