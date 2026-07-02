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
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Bank Id" , unique = true)
	private Integer bankid;
	
	@Column(name = "Bank Name")
	private String bankname;
	
	@Column (name =  "IFSC Code" , unique = true )
	private String ifscCode;
	
	@Column   (name =  "Branch Name")
	private String branchName;
	
	@Column  (name =  "Contact Number" , length = 10)
	private String contactNumber;

}
