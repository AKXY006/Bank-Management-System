package com.bank.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bank.entity.Address;
import com.bank.entity.Bank;
import com.bank.exception.CityNotFoundException;
import com.bank.exception.IdNotFoundException;
import com.bank.repository.AddressRepository;
import com.bank.repository.BankRepository;
import com.bank.util.ResponseStructure;


@Service
public class AddressService{
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private BankRepository bankRepository;
	
	public ResponseEntity<ResponseStructure<Address>> findById(Integer id){
		
	Optional<Address> optional = addressRepository.findById(id);
	
	if(optional.isEmpty()) {
		throw new IdNotFoundException("Id Not Found");
	}
	
	ResponseStructure<Address> responseStructure = new ResponseStructure<Address>();
	responseStructure.setStatusCode(HttpStatus.OK.value());
	responseStructure.setMessage("Address Found Successfully");
	responseStructure.setData(optional.get());
	
	return new ResponseEntity<>(responseStructure,HttpStatus.OK);
	
	}
	
	public ResponseEntity<ResponseStructure<Address>> updatedAddress(Integer id , Address address){
		
		Optional<Address> optional = addressRepository.findById(id);
		
		if(optional.isEmpty()) {
			throw new IdNotFoundException("Id Not Found");
		}
		
		Address newAddress = optional.get();
		newAddress.setCity(address.getCity());
		newAddress.setPincode(address.getPincode());
		newAddress.setState(address.getState());
		newAddress.setStreet(address.getStreet());
		
		Address updatedAddress = addressRepository.save(newAddress);

		
		ResponseStructure<Address> responseStructure = new ResponseStructure<Address>();
		responseStructure.setStatusCode(HttpStatus.OK.value());
		responseStructure.setMessage("Address update Successfully");
		responseStructure.setData(updatedAddress);
		
		return new ResponseEntity<>(responseStructure,HttpStatus.OK);
		
		}
	
	   public ResponseEntity<ResponseStructure<Address>> findAddressByBankId(Integer id){
       Optional<Bank> optional = bankRepository.findById(id);
		
		    if(optional.isEmpty()) {
			   throw new IdNotFoundException("Id Not Found");
		    }
			   Bank bank = optional.get();
			   Address address = bank.getAddress();
			     
			  ResponseStructure<Address> responseStructure = new ResponseStructure<Address>();
			  responseStructure.setStatusCode(HttpStatus.OK.value());
				responseStructure.setMessage("Address Found Successfully");
				responseStructure.setData(address);
				
				return ResponseEntity.ok(responseStructure);    
				
	        }
	   
	   public ResponseEntity<ResponseStructure<List<Address>>> findByCity(String city){
		   List <Address> optional = addressRepository.findByCity(city);
		   
		   if(optional.isEmpty()) {
			   throw new CityNotFoundException("City Not Found");
		    }
		   
		   ResponseStructure<List<Address>> responseStructure = new ResponseStructure<>();
		   responseStructure.setStatusCode(HttpStatus.OK.value());
		   responseStructure.setMessage("Adress Found Successfully");
		   responseStructure.setData(optional);
		   
		   return ResponseEntity.ok(responseStructure);
		   
	   }
	   
	   public ResponseEntity<ResponseStructure<List<Address>>> findByCityAndStreet(String city, String street){
		  
		   List<Address> optional = addressRepository.findByCityAndStreet(city, street);
		   if(optional.isEmpty()) {
			   throw new CityNotFoundException("City or Street Not Found");
		    }
		
		   ResponseStructure<List<Address>> responseStructure = new ResponseStructure<>();
		   responseStructure.setStatusCode(HttpStatus.OK.value());
		   responseStructure.setMessage("Adress Found Successfully");
		   responseStructure.setData(optional);
		   
		   return ResponseEntity.ok(responseStructure);  
	   }
	   
	   public ResponseEntity<ResponseStructure<List<Address>>> findByPincode(String city){
		   List <Address> optional = addressRepository.findByPincode(city);
		   
		   if(optional.isEmpty()) {
			   throw new CityNotFoundException("Pincode Not Found");
		    }
		   
		   ResponseStructure<List<Address>> responseStructure = new ResponseStructure<>();
		   responseStructure.setStatusCode(HttpStatus.OK.value());
		   responseStructure.setMessage("Adress Found Successfully");
		   responseStructure.setData(optional);
		   
		   return ResponseEntity.ok(responseStructure);
		   
	   }
	
	   
	   
	
	


}
