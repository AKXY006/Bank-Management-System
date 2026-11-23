package com.bank.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	@Column(name = "Account_Id")
	private Integer accountId;
	
	@Column(name = "Account_Number", unique = true, nullable = false)
	private Long accountNumber;
	
	@Column(name = "Account_Holder_Name", nullable = false)
	private String accountHolderName;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "Account_Type", nullable = false)
	private AccountType accountType;
	
	@Column(name = "Balance", precision = 19, scale = 2, nullable = false)
	private BigDecimal balance;
	
	@ManyToOne
	@JoinColumn(name = "Bank_Id", nullable = false)
	@JsonIgnore
	private Bank bank;
	
}
