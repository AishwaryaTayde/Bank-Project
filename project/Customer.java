package com.tech.project;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

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
	
	private int Password;
	
	@OneToOne
	private Transaction_History transaction_history;
	

}
