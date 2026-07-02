package com.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.bank.repository.AddressRepository;

@RestController
public class AddressController {
	
	@Autowired
	private AddressRepository addressRepository;

}
