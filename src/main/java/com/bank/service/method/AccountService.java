package com.bank.service.method;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.bank.dto.AccountDto;
import com.bank.entity.Account;
import com.bank.entity.AccountType;
import com.bank.util.ResponseStructure;

public interface AccountService {

	 //1)Create Account
    ResponseEntity<ResponseStructure<Account>> saveAccount(AccountDto accountDto);

    //2)Get All Account
    ResponseEntity<ResponseStructure<List<Account>>> findAllAccount();

    //3)Get Account By Id
    ResponseEntity<ResponseStructure<Account>> findById(Integer accountId);

    //4)Delete Account
    ResponseEntity<ResponseStructure<String>> deleteById(Integer accountId);

    //5)Deposit Amount
    ResponseEntity<ResponseStructure<Account>> depositAmount(Integer accountId, Double amount);

    //6)Withdraw Amount
    ResponseEntity<ResponseStructure<Account>> withdrawAmount(Integer accountId, Double amount);

    //7)Transfer Amount
    ResponseEntity<ResponseStructure<String>> transferAmount(Integer senderAccountId,Integer receiverAccountId,Double amount);

    //8)Get Account By Bank Id
    ResponseEntity<ResponseStructure<List<Account>>> findByBankId(Integer bankId);

    //9)Get Account By Type
    ResponseEntity<ResponseStructure<List<Account>>> findByAccountType(AccountType accountType);

    //10)Get Account Balance Greater Than
    ResponseEntity<ResponseStructure<List<Account>>> findByBalanceGreaterThan(Double balance);

    //11)Get Account By Pagination
    ResponseEntity<ResponseStructure<Page<Account>>> getAccountByPagination(int page,int size,String sort);

}
