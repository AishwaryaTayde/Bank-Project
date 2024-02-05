package com.tech.project;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
@Entity
public class Transaction_History {
	

	@Id
	private int TaccNum;
	
	private double DepositMoney;
	private double debitedMoney;
//	private double creditedMoney;
	private double totalBalance;


	private Date date;
	
	

	
//	
	

}
