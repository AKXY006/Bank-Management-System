package com.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.repository.AddressRepository;

@Service
public class AddressServiceImp {
	
	@Autowired
	private AddressRepository addressRepository;

}
