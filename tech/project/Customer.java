package com.tech.project;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;

import lombok.Data;

@Data 
@Entity
public class Customer {
	@Id
	private int AccNum;
	private String Name;
	private long MobNo;
	private String Address;
	private double totalBalance;
	private long Pincode;
	
	private int Password;
	
	@OneToOne
	private Transaction_History transaction_history;
	

}
