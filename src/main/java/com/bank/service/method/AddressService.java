package com.bank.service.method;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bank.dto.AddressDto;
import com.bank.entity.Address;
import com.bank.util.ResponseStructure;

public interface AddressService {
	
	//1)Get Address By Id
    ResponseEntity<ResponseStructure<Address>> findById(Integer addressId);

    //2)Update Address By Id
    ResponseEntity<ResponseStructure<Address>> updateAddress(Integer addressId, AddressDto addressDto);

    //3)Get Address By Bank Id
    ResponseEntity<ResponseStructure<Address>> findByBankId(Integer bankId);

    //4)Get Address By City
    ResponseEntity<ResponseStructure<List<Address>>> findByCity(String city);

    //5)Get Address By City And Street
    ResponseEntity<ResponseStructure<Address>> findByCityAndStreet(String city, String street);

    //6)Get Address By Pincode
    ResponseEntity<ResponseStructure<List<Address>>> findByPincode(String pincode);

}
