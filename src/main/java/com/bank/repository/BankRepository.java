package com.bank.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.entity.Bank;

public interface BankRepository extends JpaRepository<Bank, Integer>{
	 
		Optional<Bank> findByIfscCode(String ifscCode);

		Optional<Bank> findByAddressAddressId(Integer addressId);

		List<Bank> findByAddressCity(String city);
		
		Optional<Bank> findByContactNumber(String contactNumber);

}
