package com.bank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {
	
   List<Address> findAddressByCity(String city);
   
   List<Address> findAddressByCityAndStreet(String city,String street);
   
   List<Address> findAddressesByPinCode(String pincode);

}
