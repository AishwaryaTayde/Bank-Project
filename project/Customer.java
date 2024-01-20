package com.tech.project;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data 
@Entity
public class Customer {
	@Id
	private int AccNum;
	private String Name;
	private long MobNo;
	private String Address;
	private long Pincode;
	private double DepositMoney;
	private int Password;
	
	

}
