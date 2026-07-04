package com.bank.dto;

import com.bank.entity.AccountType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
	

	private Long accountNumber;
	private String accountHolderName;
	private AccountType accountType;
	private Double balance;
}
