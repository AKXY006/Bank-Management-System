package com.bank.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.entity.Bank;

import com.bank.service.BankService;
import com.bank.util.ResponseStructure;


@RestController
@RequestMapping("/bank")
public class BankController {
	
	@Autowired
	private BankService bankService;
	
	@PostMapping
	public ResponseEntity<ResponseStructure<Bank>> saveBank(@RequestBody Bank bank){
		return bankService.saveBank(bank);
	}
	
	@GetMapping
	public ResponseEntity<ResponseStructure<List<Bank>>> getAllBank(){
		return bankService.getAllBank();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Bank>> getBookById(@PathVariable Integer id){
		return bankService.getBankById(id);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<Bank>> deleteBank(@PathVariable Integer id){
		return bankService.deleteBank(id);
	}
	
	@GetMapping("/ifscCode/{ifscCode}")
	public ResponseEntity<ResponseStructure<Bank>> getBankByIfscCode(@PathVariable String ifscCode){
		return bankService.getBankByIfscCode(ifscCode);
	}
	
	@GetMapping("/AddressId/{addressId}")
	public ResponseEntity<ResponseStructure<Bank>> getByAddressId(@PathVariable Integer addressId){
		return bankService.getBankByAddressId(addressId);
	}
	
	@GetMapping("/city/{city}")
	public ResponseEntity<ResponseStructure<List<Bank>>> getByCity(@PathVariable String city){
		return bankService.getBankByCity(city);
	}
	
	
	
	
	
	
}
