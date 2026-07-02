package com.bank.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.entity.Account;
import com.bank.entity.Bank;

public interface BankRepository extends JpaRepository<Bank,Integer>{
	
	Optional<Account> findByIFSCCode(String ifscCode);

}
