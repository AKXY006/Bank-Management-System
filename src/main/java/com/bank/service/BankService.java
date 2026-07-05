package com.bank.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.bank.entity.Bank;
import com.bank.exception.IdNotFoundException;
import com.bank.exception.NoRecordAvailableException;
import com.bank.exception.RecordAlreadyExistException;
import com.bank.exception.RuleValidationException;
import com.bank.repository.BankRepository;
import com.bank.util.ResponseStructure;



@Service
public class BankService {

	@Autowired
	private BankRepository bankRepository;
	

	public ResponseEntity<ResponseStructure<Bank>> saveBank(Bank bank){
		
		Optional<Bank> ifscBank = bankRepository.findByIfscCode(bank.getIfscCode());
	    if (ifscBank.isPresent()) {
	        throw new RecordAlreadyExistException("Bank with IFSC already exists.");
	    }

	    Optional<Bank> contactBank = bankRepository.findByContactNumber(bank.getContactNumber());
	    if (contactBank.isPresent() ) {
	        throw new RecordAlreadyExistException("Bank with Contact Number already exists.");
	    }
	    
	    if (bank.getContactNumber() == null || bank.getContactNumber().length() != 10) {
	        throw new RuleValidationException("Contact Number must be exactly 10 digits.");
	    }
		
		
	    if (bank.getAddress() == null) {
	        throw new RuleValidationException("Address is mandatory");
	    }

	    if (bank.getAddress().getPincode() == null || bank.getAddress().getPincode().length() != 6) {
	        throw new RuleValidationException("Pincode must be exactly 6 digits.");
	    }

	   Bank savedBank = bankRepository.save(bank);
	   
	   ResponseStructure<Bank> responseStructure = new ResponseStructure<>();
	   responseStructure.setStatusCode(HttpStatus.CREATED.value());
	   responseStructure.setMessage("Bank Created Successfully");
	   responseStructure.setData(savedBank);
	   
	   return new ResponseEntity<>(responseStructure, HttpStatus.CREATED);
	   	
	}
     
	
	//Get All Bank
	public ResponseEntity<ResponseStructure<List<Bank>>> getAllBank(){
		
		List<Bank> banks=bankRepository.findAll();
		
		if(banks.isEmpty()) {
			throw new NoRecordAvailableException("No Record");		
		}		     
		     ResponseStructure<List<Bank>> responseStructure = new ResponseStructure<>();
		     responseStructure.setStatusCode(HttpStatus.OK.value());
		     responseStructure.setMessage("Get All Bank");
		     responseStructure.setData(banks);
		     
		     return new ResponseEntity<>(responseStructure,HttpStatus.OK);
	}
	
	
	       //Get bank by id
            public ResponseEntity<ResponseStructure<Bank>> getBankById(Integer bankId){
		
		   Optional<Bank> bank=bankRepository.findById(bankId);
		   
		   if(bank.isEmpty()) {
			   throw new IdNotFoundException("Id Not Found");
		   }
		     
		     ResponseStructure<Bank> responseStructure = new ResponseStructure<>();
		     responseStructure.setStatusCode(HttpStatus.OK.value());
		     responseStructure.setMessage("Bank found successful");
		     responseStructure.setData(bank.get());
		     
		     return new ResponseEntity<>(responseStructure,HttpStatus.OK);
	}
	
	
	
	

}
