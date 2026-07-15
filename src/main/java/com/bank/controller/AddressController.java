package com.bank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.entity.Address;
import com.bank.service.AddressService;
import com.bank.util.ResponseStructure;

@RestController
@RequestMapping("/address")
public class AddressController {
	
	@Autowired
	private AddressService addressService;
	
	@GetMapping("/{addressId}")
	public ResponseEntity<ResponseStructure<Address>> findById(@PathVariable Integer addressId){
		return addressService.findById(addressId);
	}
	
	@PutMapping("/{addressId}")
	public ResponseEntity<ResponseStructure<Address>> updatedAddress(@PathVariable Integer addressId , @RequestBody Address address){
		return addressService.updatedAddress(addressId,address);
	}
	
	@GetMapping("/bankid/{bankId}")
	public ResponseEntity<ResponseStructure<Address>> findAddressByBankId(@PathVariable Integer bankId){
		return addressService.findAddressByBankId(bankId);
	}
	
	@GetMapping("/city/{city}")
	public ResponseEntity<ResponseStructure<List<Address>>> findByCity(@PathVariable String city){
		return addressService.findByCity(city);
	}
	
	@GetMapping("city/{city}/street/{street}")
	public ResponseEntity<ResponseStructure<List<Address>>> findByCityAndStreet(@PathVariable String city, @PathVariable String street ){
		return addressService.findByCityAndStreet(city,street);
	}
	
	@GetMapping("/pincode/{pincode}")
	public ResponseEntity<ResponseStructure<List<Address>>> findByPincode(@PathVariable String pincode){
		return addressService.findByPincode(pincode);
	}

}
