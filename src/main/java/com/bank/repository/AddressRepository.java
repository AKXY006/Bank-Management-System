package com.bank.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Integer>{
	
	//1) Get Address By id --> GetMapping (save());
	
	//2) Update Mapping By id ---> PutMapping / PatchMapping ( findById() );
	
	//3) Get Address By bankId ---> GetMapping ( findById() );
	
	//4) Get Address By city ----> GetMapping (finByCity() );
	List<Address>  finByCity(String city);
	
	//5) Get Address By City And Street   ---> GetMapping (findByCityAndStreet());
	Optional<Address> findByCityAndStreet(String city, String street);
	
	//6) Get Address By pincode  ----> GetMapping (findByPincode());
	List<Address> findByPincode(String pincode);
	
	
	

}
