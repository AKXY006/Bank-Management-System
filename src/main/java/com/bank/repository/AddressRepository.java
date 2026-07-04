package com.bank.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Integer>{
	
	//1) Get Address By id --> GetMapping (findById());    
	
	//2) Update Mapping By id ---> PutMapping / PatchMapping ( findById() + save() );
	
	//3) Get Address By bankId ---> GetMapping ( findById() );
	Optional<Address> findByBankBankId(Integer bankId);
	
	//4) Get Address By city ----> GetMapping (finByCity() );
	List<Address>  findByCity(String city);
	
	//5) Get Address By City And Street   ---> GetMapping (findByCityAndStreet());
	Optional<Address> findByCityAndStreet(String city, String street);
	
	//6) Get Address By Pincode  ----> GetMapping (findByPincode());
	List<Address> findByPincode(String pincode);

}
