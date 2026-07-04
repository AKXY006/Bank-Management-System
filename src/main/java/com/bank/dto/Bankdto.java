package com.bank.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankDto {

    private String bankName;
	private String ifscCode;
	private String branchName;
	private String contactNumber;
	
	private AddressDto address;
	private List<AccountDto> accounts;
	
}
