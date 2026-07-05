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
	    
	    @ExceptionHandler(NoRecordAvailableException.class)
	    public ResponseEntity<ResponseStructure<String>> handleNoRecordAvailableException(NoRecordAvailableException exception){
	    	
	    	ResponseStructure<String> responseStructure = new ResponseStructure<String>();
	    	responseStructure.setStatusCode(HttpStatus.NOT_FOUND.value());
	    	responseStructure.setMessage("Failed");
	    	responseStructure.setData(exception.getMessage());
	    	
	    	return new ResponseEntity<>(responseStructure,HttpStatus.NOT_FOUND);
	    }
	    
	    @ExceptionHandler(RecordAlreadyExistException.class)
	    public ResponseEntity<ResponseStructure<String>> handleRecordAlreadyExistException(RecordAlreadyExistException exception){
	    	
	    	ResponseStructure<String> responseStructure = new ResponseStructure<String>();
	    	responseStructure.setStatusCode(HttpStatus.CONFLICT.value());
	    	responseStructure.setMessage("Record Already Present");
            responseStructure.setData(exception.getMessage());
            
            return new ResponseEntity<>(responseStructure,HttpStatus.CONFLICT);
	    	}
	    
	    @ExceptionHandler(RuleValidationException.class)
	    public ResponseEntity<ResponseStructure<String>> handleRuleValidationException(RuleValidationException exception){
	    	
	    	ResponseStructure<String> responseStructure = new ResponseStructure<String>();
	    	responseStructure.setStatusCode(HttpStatus.BAD_REQUEST.value());
	    	responseStructure.setMessage("Record Already Present");
            responseStructure.setData(exception.getMessage());
            
            return new ResponseEntity<>(responseStructure,HttpStatus.BAD_REQUEST);
	    	}
	    
	    	
	    	}
	   
	    
	    
         


