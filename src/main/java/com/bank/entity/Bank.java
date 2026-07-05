package com.bank.entity;


import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
	
	@Column(name =  "IFSC_Code" , unique = true )
	private String ifscCode;
	
	@Column(name =  "Branch_Name")
	private String branchName;
	
	@Column(name ="Contact_Number" , length = 10 , unique = true)
	private String contactNumber;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Address_Id")
	private Address address;
	

	@OneToMany(mappedBy = "bank" , cascade = CascadeType.ALL)
	private List<Account> accounts = new ArrayList<Account>();
	

}
