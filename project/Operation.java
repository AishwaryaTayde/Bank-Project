package com.tech.project;

import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class Operation {

	char a;
	SessionFactory sf = HibernateUtil.getsessionFactory();
	Session session = sf.openSession();

	Scanner sc = new Scanner(System.in);
	Customer ac = new Customer();

// create account
public void createAccount()
	{
		while (true) 
		{

			try 
			{

				Transaction t = session.beginTransaction();// transaction strt

				System.out.println("Enter Account Number");
				ac.setAccNum(sc.nextInt());

				System.out.println("Enter Customer Name");
				ac.setName(sc.next());

				System.out.println("Enter Customer Address");
				ac.setAddress(sc.next());

				System.out.println("Enter pincode");
				ac.setPincode(sc.nextLong());

				System.out.println("Enter Mobile Number");
				ac.setMobNo(sc.nextLong());

				System.out.println("Set Password");
				ac.setPassword(sc.nextInt());

				while (true)
				{
					System.out.println("Add money");
					double d = sc.nextDouble();
					
					if (d >= 500)// money must be greater than or euqal 500
					{
						ac.setDepositMoney(d);
						System.out.println("Money has been deposited successfully");
						break;
					}
					else 
					{
						System.out.println("Money should be greater than or equal to 500");
					}
				}

				session.save(ac);
				
				t.commit();
				
				System.out.println("Account has been added successesfully");
				
			} 
			catch (Exception e) 
			{
				System.out.println("Invalid Input");
			}
			break;
		}
	}


//Show account info
public void showAccountDetails() {

		while (true) {
			try {

				System.out.println("Enter Account Number:");
				int AN = sc.nextInt();

				Customer Info = session.get(Customer.class, AN);// get account number from db
				System.out.println(Info);// read it
				break;
			}
			catch (Exception e)
			{
				System.out.println("Invalid Account");
			}

		}
	}

//Check balance	
public void balanceEnquiry() 
{
		while (true) 
		{
			try
			{
				System.out.println("Enter Account Number:");
				int AN = sc.nextInt();
				Customer Info = session.get(Customer.class, AN);// get account

				System.out.println("Enter Password");
				int pass = sc.nextInt();
				Customer check = session.get(Customer.class, pass);// get password

				System.out.println("your Balance is: " + Info.getDepositMoney());// read balance
				break;
			}

			catch (Exception e)
			{
				System.out.println("Invalid Input");
			}
		}
}


//Add Money to account
	public void depositMoney() 
	{
		while (true)
		{

			try
			{
				System.out.println("Enter Account Number:");
				int AN = sc.nextInt();
				Customer Info = session.get(Customer.class, AN);// get account from db

				System.out.println("Enter Amount");
				int amount = sc.nextInt();
				
				if (amount > 0) // amount must be greater than 0
				{
					
//	    	double dd=Info.getDepositMoney();
//	    	double d=amount + dd;
//	    	or

					double d = amount + Info.getDepositMoney(); // get previous balance from db and add amount in it

					Info.setDepositMoney(d); // update new added money to account

					session.beginTransaction().commit(); // make transaction to db

					System.out.println("Money credited successfully");
					break;
				}
			}
			catch (Exception e)
			{
				System.out.println("Invalid data");
			}
		}
	}

	
//Take money from account
	public void withdrawAmount() {
		while (true) {

			try {
				System.out.println("Enter Account Number:");
				int AN = sc.nextInt();
				Customer Info = session.get(Customer.class, AN); // get account from db

				System.out.println("Enter Amount");
				int amount = sc.nextInt();

				// amount must be greater than 0 but less than account balance
				if (amount <= Info.getDepositMoney() && amount > 0) 
				{

					double d = Info.getDepositMoney() - amount; // amount debit from account balance

					Info.setDepositMoney(d);// update debited money to account

					session.beginTransaction().commit();

					System.out.println("Money debited successfully");
					break;
				} 
				else
				{
					System.out.println("Amount should not be less than 0 or greater than Account balance");
				}
			}
			catch (Exception e)
			{
				System.out.println("Invalid data");
			}
		}
}

	
//Transfer money from one account to other
public void transferMoney() 
{

		while (true)
		{
			try 
			{

				// transfer money to
				System.out.println("Enter account number in which you want to transfer money ");
				int AN = sc.nextInt();
				Customer Transfer = session.get(Customer.class, AN);// get account from db

				// transfer money from
				System.out.println("Enter Account number from which you want to transfer money ");
				int AN2 = sc.nextInt();
				Customer User = session.get(Customer.class, AN2);// get account from db

				System.out.println("Enter Amount");
				int amount = sc.nextInt();

				// amount must greater than 0 but less than account balance
				if (amount <= User.getDepositMoney() && amount > 0) 
				{

					// add money to transferred account
					double transfer = Transfer.getDepositMoney() + amount;
					Transfer.setDepositMoney(transfer);

					// debit account money from which account you transfer amount
					double input = User.getDepositMoney() - amount;
					User.setDepositMoney(input);

					session.beginTransaction().commit();// make transaction to db

					System.out.println("Transaction Done Successfully");
					break;

				}
				else 
				{
					System.out.println("Amount should not be less than 0 or greater than Account balance");
				}
			} 
			catch (Exception e) 
			{
				System.out.println("Something went wrong, Please try again");
			}
		}
	}

//Close sf and Session
public void closeConnection()
{
	session.close();
	sf.close();
	
}
}
