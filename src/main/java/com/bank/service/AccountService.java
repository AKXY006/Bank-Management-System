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
import com.bank.exception.NoRecordAvailableException;
import com.bank.exception.RuleValidationException;
import com.bank.repository.AccountRepository;
import com.bank.repository.BankRepository;
import com.bank.util.ResponseStructure;

import jakarta.transaction.Transactional;


@Service
public class AccountService{
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private BankRepository bankRepository;
	
	//1) create account
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
	
	
	//2)get all accounts
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
	
	
	//3)find by id
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
	
	    //4) delete by id
	    public ResponseEntity<ResponseStructure<Account>> deleteById(Integer accountId){
		Optional<Account> optional = accountRepository.findById(accountId);
		
		if(optional.isEmpty()) {
			throw new IdNotFoundException("Id Not Found");
		}
		    accountRepository.deleteById(accountId);		
		    ResponseStructure<Account> responseStructure = new ResponseStructure<>();
		    responseStructure.setStatusCode(HttpStatus.OK.value());
		    responseStructure.setMessage("Accounts delete Successfully");
		    responseStructure.setData(optional.get());

		    return new ResponseEntity<>(responseStructure, HttpStatus.OK);
	    }
	
	    
	    //5)deposite Amount
	     public ResponseEntity<ResponseStructure<Account>> depositeAmount(Long accountNUmber, Integer depositebalance){
	    	 
	    	 if(depositebalance<=0) {
	    		 throw new RuleValidationException("deposite amount must greater than 0");
	    	 }
	    	 
	    	 Optional<Account> optional = accountRepository.findByAccountNumber(accountNUmber);
	    	 
	    	 if(optional.isEmpty()) {
	    		 throw new AccountNotFoundException("Invalid account number");
	    	 }
	    	 
	    	 Account account = optional.get();
	    	 account.setBalance(account.getBalance()+depositebalance);
	    	 Account savedAccount = accountRepository.save(account);
	    	 
	    	 ResponseStructure<Account> responseStructure = new ResponseStructure<>();
	    	 responseStructure.setStatusCode(HttpStatus.OK.value());
	    	 responseStructure.setMessage("Amount deposite successfully");
	    	 responseStructure.setData(savedAccount);
	    	 
	    	 return new ResponseEntity<>(responseStructure,HttpStatus.OK);
	    	 
	     }
	     
	   //6) Withdraw Amount
	     public ResponseEntity<ResponseStructure<Account>> withdrawAmount(Long accountNumber, Integer withdrawalAmount) {

	         if (withdrawalAmount <= 0) {
	             throw new RuleValidationException("withdrawal amount must be greater than 0");
	         }

	         Optional<Account> optional = accountRepository.findByAccountNumber(accountNumber);
	         if (optional.isEmpty()) {
	             throw new AccountNotFoundException("account number invalid");
	         }

	         Account account = optional.get();

	         if (withdrawalAmount > account.getBalance()) {
	             throw new RuleValidationException("Insufficient Balance");
	         }

	         account.setBalance(account.getBalance() - withdrawalAmount);
	         Account savedAccount = accountRepository.save(account);

	         ResponseStructure<Account> responseStructure = new ResponseStructure<>();
	         responseStructure.setStatusCode(HttpStatus.OK.value());
	         responseStructure.setMessage("Amount Withdrawn Successfully");
	         responseStructure.setData(savedAccount);

	         return new ResponseEntity<>(responseStructure, HttpStatus.OK);
	     }
	    
	     
	     //7)transfer amount 
	     @Transactional
	     public ResponseEntity<ResponseStructure<Account>> transferAmount(Long senderAccountNumber , Long receiverAccountNumber , Integer transferbalance){
	    	 Optional<Account> senderaccount = accountRepository.findByAccountNumber(senderAccountNumber);
	    	 if(senderaccount.isEmpty()) {
	    		 throw new AccountNotFoundException("Sender account number not exist");
	    	 }
	    	 
	    	 Optional<Account> receiveraccount = accountRepository.findByAccountNumber(receiverAccountNumber);
	    	 if(receiveraccount.isEmpty()) {
	    		 throw new AccountNotFoundException("Sender account number not exist");
	    	 }
	    	 
	    	 if (transferbalance <= 0) {
	             throw new RuleValidationException("transfer amount must be greater than 0");
	         }
	    	 
	    
	    	 Account saccount = senderaccount.get();
	    	 Account raccount = receiveraccount.get();
	    	 
	    	 
	    	 if(transferbalance<=saccount.getBalance() ) {
	    		   
	    		 saccount.setBalance(saccount.getBalance()-transferbalance);
	    		 Account savaccount = accountRepository.save(saccount);
	    		 
	    		 raccount.setBalance(raccount.getBalance()+transferbalance);
	    		 Account recacount = accountRepository.save(raccount);
	    		 
	    	 } else {
	    		 throw new RuleValidationException("Insufficient balance");
	    	 }
	    	 
	    	 ResponseStructure<Account> responseStructure = new ResponseStructure<>();
	    	 responseStructure.setStatusCode(HttpStatus.OK.value());
	    	 responseStructure.setMessage("Transfer Successful");
	    	 responseStructure.setData(saccount);
	    	 
	    	 return new ResponseEntity<>(responseStructure,HttpStatus.OK);
	    	 }

	     //8) get by bank id
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
	     
	   
	//9)get by account type
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
	
	//10) get by account balance > value
	public ResponseEntity<ResponseStructure<List<Account>>>  findByAccountBalanceGreaterThan(Double balance){
		
		List<Account> accounts = accountRepository.findByBalanceGreaterThan(balance);
		
		if(accounts.isEmpty()) {
			throw new NoRecordAvailableException("No Account found");
		}
		
		ResponseStructure<List<Account>> responseStructure = new ResponseStructure<>();
		responseStructure.setStatusCode(HttpStatus.OK.value());
		responseStructure.setMessage("account found successful");
		responseStructure.setData(accounts);
		
		return new ResponseEntity<>(responseStructure,HttpStatus.OK);
	}
	
	//11) get by pagination and sorting
	
	

	


}
