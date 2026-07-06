package com.bank.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.entity.Account;
import com.bank.entity.AccountType;

public interface AccountRepository extends JpaRepository<Account, Integer>{

	List<Account> findByBankBankId(Integer bankId);
	

	List<Account> findByAccountType(AccountType accountType);

	List<Account> findByBalanceGreaterThan(Double balance);
	
	Optional<Account> findByAccountNumber(Long accountNumber);
	

	

}
