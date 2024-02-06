package com.tech.project;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		while(true)
		{
		Scanner sc=new Scanner(System.in);
		Operation op=new Operation();
		System.out.println("Enter below choice");
		System.out.println("1. Create Account \n2. Show Account Info \n3. Check Balance \n4. Deposit Money \n5. Withdraw Money");
		System.out.println("6. Transfer Money \n7. Get Account Statements");
		System.out.println("Press 0 to close connection");
		int a=sc.nextInt();
		switch(a)
		{
		case 1: op.createAccount();
		break;
		
		case 2: op.showAccountDetails();
		break;
		
		case 3: op.balanceEnquiry();
		break;
		
		case 4: op.depositMoney();
		break;
		
		case 5: op.withdrawAmount();
		break;
		
		case 6: op.transferMoney();
		break;
		
//		case 7:op.getStatement();
//		break;
		
		case 0: op.closeConnection();
		break;
			
		}
		}		
	}

}
