package com.bank.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Address {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="Address_Id")
	private Integer addressId;
	
	@Column(name = "Street", nullable = false)
	private String street;
	
	@Column(name = "City", nullable = false)
	private String city;
	
	@Column(name = "State", nullable = false)
	private String state;
	
	@Column(name = "Pincode", length = 6, nullable = false)
	private String pincode;
	
	@OneToOne(mappedBy = "address")
	@JsonIgnore
	private Bank bank;

}
