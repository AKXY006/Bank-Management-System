package com.bank.exception;

public class AccountNumberAlreadyExitException extends RuntimeException{
	
	public AccountNumberAlreadyExitException(String message) {
		super(message);
	}

}
