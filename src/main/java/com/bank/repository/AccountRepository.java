package com.bank.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.entity.Account;
import com.bank.entity.AccountType;

public interface AccountRepository extends JpaRepository<Account, Integer>{
	
	//1) Create Account ----> PostMapping (save());
	
	//2) Get All Account ----> GetMapping (findByAll());
	
	//3) Get Account By Id ----> GetMapping (findById());
	
	//4) Delete Account ----> DeleteMapping (findById());
	
	//5) Deposit Amount

	//6) Withdraw Amount
	
	//7) Transfer Amount
	
	//8) Get Account By BankId
	List<Account> findByBankBankId(Integer bankId);
	
	//9) Get Account By Type
	List<Account> findByAccountType(AccountType accountType);
	
	//10) Get Account Balance GreaterThan Value
	List<Account> findByBalanceGreaterThan(Double balance);
	
	//11) Get Account By Pagination 
	

}
