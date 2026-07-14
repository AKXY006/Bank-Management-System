package com.bank.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
	
	   //1) create bank
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
	    
	    if (bank.getAddress().getPincode() == null|| bank.getAddress().getPincode().length() != 6) {
	        throw new RuleValidationException("Pincode must be exactly 6 digits.");
	    }


	   Bank savedBank = bankRepository.save(bank);
	   
	   ResponseStructure<Bank> responseStructure = new ResponseStructure<>();
	   responseStructure.setStatusCode(HttpStatus.CREATED.value());
	   responseStructure.setMessage("Bank Created Successfully");
	   responseStructure.setData(savedBank);
	   
	   return new ResponseEntity<>(responseStructure, HttpStatus.CREATED);
	   	
	  }
     
	         //2) get all bank
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
	
	
              //3) find by id
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
            
            
        	    //4) Delete Bank
                public ResponseEntity<ResponseStructure<Bank>> deleteBank(Integer bankId){
            	
            	Optional<Bank> optional = bankRepository.findById(bankId);
            	
            	if(optional.isEmpty()) {
            		throw new IdNotFoundException("Bank Id Does Not Exist");
            	}
            	
            	Bank bank = optional.get();
            	 
            	if (!bank.getAccounts().isEmpty()) {
            	    throw new RuleValidationException("Bank cannot be deleted because it has active accounts");
            	}
            	 
                bankRepository.deleteById(bankId);
                 
    	        ResponseStructure<Bank> responseStructure = new ResponseStructure<>();
    		    responseStructure.setStatusCode(HttpStatus.OK.value());
    		    responseStructure.setMessage("Bank Delete successful");
    		    responseStructure.setData(bank);
    		
    		    return new ResponseEntity<>(responseStructure,HttpStatus.OK);  
               }
                

                   //5) Get by Pagination and Sorting
                   public ResponseEntity<ResponseStructure<Page<Bank>>> getBankByPaginationAndSorting(int pn,int ps,String field) {

                    Pageable pageable = PageRequest.of(pn, ps, Sort.by(field));
                    Page<Bank> page = bankRepository.findAll(pageable);

                    if (page.isEmpty()) {
                        throw new NoRecordAvailableException("No Banks Found");
                    }

                    ResponseStructure<Page<Bank>> responseStructure = new ResponseStructure<>();
                    responseStructure.setStatusCode(HttpStatus.OK.value());
                    responseStructure.setMessage("Banks Found Successfully");
                    responseStructure.setData(page);

                    return new ResponseEntity<>(responseStructure, HttpStatus.OK);
                   }

            
                 //6)get by ifsccode
                 public ResponseEntity<ResponseStructure<Bank>> getBankByIfscCode(String ifscCode){
        		
     		     Optional<Bank> bank=bankRepository.findByIfscCode(ifscCode);
     		   
     		     if(bank.isEmpty()) {
     			     throw new IdNotFoundException("ifscCode  Not Found");
     		     }

     		     ResponseStructure<Bank> responseStructure = new ResponseStructure<>();
     		     responseStructure.setStatusCode(HttpStatus.OK.value());
     		     responseStructure.setMessage("Bank found successfully");
     		     responseStructure.setData(bank.get());
     		     
     		     return new ResponseEntity<>(responseStructure,HttpStatus.OK);
     	         }
            
            
                 //7) Get Bank By AddressId
                 public ResponseEntity<ResponseStructure<Bank>> getBankByAddressId(Integer id){
        		
      		     Optional<Bank> bank=bankRepository.findByAddressAddressId(id);
      		   
      		     if(bank.isEmpty()) {
      			     throw new IdNotFoundException("AddressId does Not Found");
      		     }

      		     ResponseStructure<Bank> responseStructure = new ResponseStructure<>();
      		     responseStructure.setStatusCode(HttpStatus.OK.value());
      		     responseStructure.setMessage("Bank found successful");
      		     responseStructure.setData(bank.get());
      		     
      		     return new ResponseEntity<>(responseStructure,HttpStatus.OK);
      	        }
            
                //8) Get Bank By City
                public ResponseEntity<ResponseStructure<List<Bank>>> getBankByCity(String city){
        		
       		    List<Bank> banks=bankRepository.findByAddressCity(city);
       		    
       		    if(banks.isEmpty()) {
       			    throw new NoRecordAvailableException("No Banks Found In This City");
       		    }
       		  
       		     ResponseStructure<List<Bank>> responseStructure = new ResponseStructure<>();
       		     responseStructure.setStatusCode(HttpStatus.OK.value());
       		     responseStructure.setMessage("Bank found successful");
       		     responseStructure.setData(banks);
       		     
       		     return new ResponseEntity<>(responseStructure,HttpStatus.OK);
       	        }
            
                     //9) get Bank By ContactNumber
                     public ResponseEntity<ResponseStructure<Bank>> getBankByContactNumber(String contactNumber){
        		     Optional<Bank> bank=bankRepository.findByContactNumber(contactNumber);
        		   
        		     if(bank.isEmpty()) {
        			      throw new NoRecordAvailableException("No Bank Found With This Contact Number");
        		     }
        		     ResponseStructure<Bank> responseStructure = new ResponseStructure<>();
        		     responseStructure.setStatusCode(HttpStatus.OK.value());
        		     responseStructure.setMessage("Bank found successfully");
        		     responseStructure.setData(bank.get());
        		     
        		     return new ResponseEntity<>(responseStructure,HttpStatus.OK);
        	         }        
   }
           
	
	
	


