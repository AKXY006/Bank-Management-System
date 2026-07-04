package com.bank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bank.util.ResponseStructure;

@RestControllerAdvice
public class MyException extends ResponseEntityExceptionHandler {
	
	
	    @ExceptionHandler (IdNotFoundException.class)
		public ResponseEntity<ResponseStructure<String>> handleIdNotFoundException(IdNotFoundException exception){
	    	
	    	ResponseStructure<String> responseStructure = new ResponseStructure<String>();
	    	responseStructure.setStatusCode(HttpStatus.NOT_FOUND.value());
	    	responseStructure.setMessage("Failed");
	    	responseStructure.setData(exception.getMessage());
	    	
	    	return new ResponseEntity<>(responseStructure,HttpStatus.NOT_FOUND);
	}
}
