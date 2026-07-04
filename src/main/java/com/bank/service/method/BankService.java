package com.bank.service.method;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.bank.dto.BankDto;
import com.bank.entity.Bank;
import com.bank.util.ResponseStructure;

public interface BankService {
	
	//1)Create Bank
	ResponseEntity<ResponseStructure<Bank>> saveBank(BankDto bankDto);
	
	//2)Get All Bank
	ResponseEntity<ResponseStructure<List<Bank>>> findAllBank();
	
	//3)Get Bank By Id
	ResponseEntity<ResponseStructure<Bank>> findById(Integer bankId);
	
	//4)Delete Bank
	ResponseEntity<ResponseStructure<String>> deleteById(Integer bankId);
	
	//5)Get Bank By Pagination and Sorting 
	ResponseEntity<ResponseStructure<Page<Bank>>> getBankByPagination(int page, int size, String sort);
	
	//6)Get by ifscCode
	ResponseEntity<ResponseStructure<Bank>> findByIfscCode(String ifscCode);
	
	//7) Get Bank By AddressId
	ResponseEntity<ResponseStructure<Bank>> findByAddressId(Integer addressId);
	
	//8) Get Bank By City 
	ResponseEntity<ResponseStructure<List<Bank>>> findByCity(String city);
	
	//9) Get Bank By ContactNo
	ResponseEntity<ResponseStructure<Bank>> findByContactNumber(String contactNumber);
	
	
	

}
