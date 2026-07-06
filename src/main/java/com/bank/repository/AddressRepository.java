package com.bank.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Integer>{
	

	Optional<Address> findByBankBankId(Integer bankId);

	List<Address>  findByCity(String city);

	List<Address> findByCityAndStreet(String city, String street);

	List<Address> findByPincode(String pincode);



}
