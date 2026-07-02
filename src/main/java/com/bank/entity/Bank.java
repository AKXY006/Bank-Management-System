package com.bank.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Bank {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Bank_Id")
	private Integer bankId;
	
	@Column(name = "Bank_Name")
	private String bankName;
	
	@Column (name =  "IFSC_Code" , unique = true )
	private String ifscCode;
	
	@Column   (name =  "Branch_Name")
	private String branchName;
	
	@Column  (name =  "Contact_Number" , length = 10)
	private String contactNumber;

}
