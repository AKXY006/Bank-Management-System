package com.bank.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.entity.Bank;

public interface BankRepository extends JpaRepository<Bank, Integer>{
	 
	    //1) Create bank ---> PostMapping (save());
	
		//2) Get all Bank ---> GetMapping (saveAll());
		
		//3) Get Bank By id ----> GetMapping (findById());
		
		//4) Delete Bank ---> DeleteMapping (findById());
		
		//5) Get Bank By Pagination and Sorting 
		
		//6) Get by ifscCode
		Optional<Bank> findByIfscCode(String ifscCode);
		
		//7) Get Bank By AddressId
		Optional<Bank> findByAddressAddressId(Integer addressId);
		
		//8) Get Bank By City ---> GetMapping 
		List<Bank> findByAddressCity(String city);
		
		//9) Get Bank By ContactNo ---> GetMapping 
		Optional<Bank> findByContactNumber(String contactNumber);

}
