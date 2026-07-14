package com.bank.service;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bank.entity.Account;
import com.bank.entity.AccountType;
import com.bank.entity.Bank;
import com.bank.exception.AccountNotFoundException;
import com.bank.exception.AccountNumberAlreadyExistException;
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
			throw new AccountNumberAlreadyExistException("Account number already exists");
		}
		
		if(account.getAccountType() == AccountType.SAVING && account.getBalance() < 1000){
		    throw new RuleValidationException("Minimum balance for Savings Account is 1000");
		}

		if(account.getAccountType() == AccountType.CURRENT && account.getBalance() < 5000){
		    throw new RuleValidationException("Minimum balance for Current Account is 5000");
		}

		if(account.getAccountType() == AccountType.FIXED_DEPOSIT && account.getBalance() < 10000){
		    throw new RuleValidationException("Minimum balance for Fixed Deposit Account is 10000");
		}
		
		account.setBank(optionalBank.get());
		 
		Account savedAccount = accountRepository.save(account);
		
		ResponseStructure<Account> responseStructure = new ResponseStructure<>();
		responseStructure.setStatusCode(HttpStatus.CREATED.value());
		responseStructure.setMessage("Account Created successful");
		responseStructure.setData(savedAccount);
		
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
		    responseStructure.setMessage("Accounts Found Successfully");
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
		    responseStructure.setMessage("Accounts deleted Successfully");
		    responseStructure.setData(optional.get());

		    return new ResponseEntity<>(responseStructure, HttpStatus.OK);
	       }
	
	    
	        //5)deposite Amount
	        public ResponseEntity<ResponseStructure<Account>> depositAmount(Long accountNumber, Double depositBalance){
	    	 
	    	 if(depositBalance<=0) {
	    		 throw new RuleValidationException("Deposit amount must be greater than 0");
	    	 }
	    	 
	    	 Optional<Account> optional = accountRepository.findByAccountNumber(accountNumber);
	    	 
	    	 if(optional.isEmpty()) {
	    		 throw new AccountNotFoundException("Invalid account number");
	    	 }
	    	 
	    	 Account account = optional.get();
	    	 account.setBalance(account.getBalance()+depositBalance);
	    	 Account savedAccount = accountRepository.save(account);
	    	 
	    	 ResponseStructure<Account> responseStructure = new ResponseStructure<>();
	    	 responseStructure.setStatusCode(HttpStatus.OK.value());
	    	 responseStructure.setMessage("Amount deposited successfully");
	    	 responseStructure.setData(savedAccount);
	    	 
	    	 return new ResponseEntity<>(responseStructure,HttpStatus.OK);	 
	        }
	     
	        //6) Withdraw Amount
	        public ResponseEntity<ResponseStructure<Account>> withdrawAmount(Long accountNumber, Double withdrawalAmount) {

	         if (withdrawalAmount <= 0) {
	             throw new RuleValidationException("Withdrawal amount must be greater than 0");
	         }

	         Optional<Account> optional = accountRepository.findByAccountNumber(accountNumber);
	         if (optional.isEmpty()) {
	             throw new AccountNotFoundException("Invalid account number");
	         }

	         Account account = optional.get();

	         if (withdrawalAmount > account.getBalance()) {
	             throw new RuleValidationException("Insufficient Balance");
	         }

	         Double remainingBalance = account.getBalance() - withdrawalAmount;

	         if (account.getAccountType() == AccountType.SAVING && remainingBalance < 1000) {
	             throw new RuleValidationException("Minimum balance for Savings Account is 1000");
	         }

	         if (account.getAccountType() == AccountType.CURRENT && remainingBalance < 5000) {
	             throw new RuleValidationException("Minimum balance for Current Account is 5000");
	         }

	         if (account.getAccountType() == AccountType.FIXED_DEPOSIT && remainingBalance < 10000) {
	             throw new RuleValidationException("Minimum balance for Fixed Deposit Account is 10000");
	         }

	         account.setBalance(remainingBalance);
	         Account savedAccount = accountRepository.save(account);

	         ResponseStructure<Account> responseStructure = new ResponseStructure<>();
	         responseStructure.setStatusCode(HttpStatus.OK.value());
	         responseStructure.setMessage("Amount withdrawn successfully");
	         responseStructure.setData(savedAccount);

	         return new ResponseEntity<>(responseStructure, HttpStatus.OK);
	     }
	    
	     
	            //7)transfer amount 
	            @Transactional
	            public ResponseEntity<ResponseStructure<Account>> transferBalance(Long senderAccountNumber,Long receiverAccountNumber,Double transferBalance) {

	            Optional<Account> senderAccount = accountRepository.findByAccountNumber(senderAccountNumber);
	            if (senderAccount.isEmpty()) {
	                throw new AccountNotFoundException("Sender account number does not exist");
	            }

	            Optional<Account> receiverAccount = accountRepository.findByAccountNumber(receiverAccountNumber);
	            if (receiverAccount.isEmpty()) {
	                throw new AccountNotFoundException("Receiver account number does not exist");
	            }

	            if (transferBalance <= 0) {
	                throw new RuleValidationException("Transfer amount must be greater than 0");
	            }

	            Account sAccount = senderAccount.get();
	            Account rAccount = receiverAccount.get();

	            if (transferBalance > sAccount.getBalance()) {
	                throw new RuleValidationException("Insufficient balance");
	            }

	            Double remainingBalance = sAccount.getBalance() - transferBalance;

	            if (sAccount.getAccountType() == AccountType.SAVING && remainingBalance < 1000) {
	                throw new RuleValidationException("Minimum balance for Savings Account is 1000");
	            }

	            if (sAccount.getAccountType() == AccountType.CURRENT && remainingBalance < 5000) {
	                throw new RuleValidationException("Minimum balance for Current Account is 5000");
	            }

	            if (sAccount.getAccountType() == AccountType.FIXED_DEPOSIT && remainingBalance < 10000) {
	                throw new RuleValidationException("Minimum balance for Fixed Deposit Account is 10000");
	            }

	            sAccount.setBalance(remainingBalance);
	            Account savedAccount = accountRepository.save(sAccount);

	            rAccount.setBalance(rAccount.getBalance() + transferBalance);
	            accountRepository.save(rAccount);

	            ResponseStructure<Account> responseStructure = new ResponseStructure<>();
	            responseStructure.setStatusCode(HttpStatus.OK.value());
	            responseStructure.setMessage("Transfer Successful");
	            responseStructure.setData(savedAccount);

	            return new ResponseEntity<>(responseStructure, HttpStatus.OK);
	            }

	         //8) get by bank id
	         public ResponseEntity<ResponseStructure<List<Account>>> findByBankId(Integer bankId){
		     Optional<Bank> optional = bankRepository.findById(bankId);
		
		     if(optional.isEmpty()) {
		     throw new IdNotFoundException("Id Not Found");
		     }
		
		     List<Account> accounts = optional.get().getAccounts();

		     if (accounts.isEmpty()) {
		         throw new AccountNotFoundException("No Accounts Found");
		     }
		
		     ResponseStructure<List<Account>> responseStructure = new ResponseStructure<>();
		     responseStructure.setStatusCode(HttpStatus.OK.value());
		     responseStructure.setMessage("Accounts found Successfully");
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
			      throw new NoRecordAvailableException("No accounts found");
		      }
		
		      ResponseStructure<List<Account>> responseStructure = new ResponseStructure<>();
		      responseStructure.setStatusCode(HttpStatus.OK.value());
		      responseStructure.setMessage("Accounts found successful");
		      responseStructure.setData(accounts);
		
		      return new ResponseEntity<>(responseStructure,HttpStatus.OK);
	          }
	
	          //11) Get By Pagination And Sorting
	          public ResponseEntity<ResponseStructure<Page<Account>>> findByPaginationAndSorting(int pn,int ps,String field) {

	          Pageable pageable = PageRequest.of(pn, ps, Sort.by(field));

	          Page<Account> page = accountRepository.findAll(pageable);

	          if (page.isEmpty()) {
	              throw new NoRecordAvailableException("No Accounts Found");
	          }

	          ResponseStructure<Page<Account>> responseStructure = new ResponseStructure<>();
	          responseStructure.setStatusCode(HttpStatus.OK.value());
	          responseStructure.setMessage("Accounts Found Successfully");
	          responseStructure.setData(page);

	          return new ResponseEntity<>(responseStructure, HttpStatus.OK);
	          }
}
