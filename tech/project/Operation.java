package com.tech.project;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class Operation {

	char a;
	SessionFactory sf = HibernateUtil.getsessionFactory();
	Session session = sf.openSession();

	Scanner sc = new Scanner(System.in);
	Customer ac = new Customer();

	Transaction_History th = new Transaction_History();
	Date thisdate = new Date();

// create account
	public void createAccount() {
		while (true) {

			try {

				Transaction t = session.beginTransaction();// transaction strt

				System.out.println("Enter Account Number");
				ac.setAccNum(sc.nextInt());
				th.setTaccNum(ac.getAccNum());// set account no in transaction table also

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

				while (true) {
					System.out.println("Add money");
					double d = sc.nextDouble();

					if (d >= 500)// money must be greater than or euqal 500
					{
						th.setCreditedMoney(d);
						
						ac.setTotalBalance(d);
						
						th.setDate(thisdate);
						
						System.out.println("Money has been deposited successfully");

						break;
					} else {
						System.out.println("Money should be greater than or equal to 500");
					}
				}

				ac.setTransaction_history(th);
			
				session.save(ac);
				session.save(th);

				t.commit();

				System.out.println("Account has been added successesfully");

			} catch (Exception e) {
				System.out.println("Invalid Input");
			}
			break;
		}
	}

//Show account info
	public void showAccountDetails() {

		while (true) {
			try {
				System.out.println("Select below choice: \n1. Get single account data  \n2. Get all Accounts data");

				int x = sc.nextInt();

				if (x == 1) {
					System.out.println("Enter Account Number:");
					int AN = sc.nextInt();

					Customer Info = session.get(Customer.class, AN);// get account number from db

					System.out.println("Account No: " + Info.getAccNum());
					System.out.println("Name: " + Info.getName());
					System.out.println("Address: " + Info.getAddress());
					System.out.println("Pincode: " + Info.getPincode());
					System.out.println("Mobile No: " + Info.getMobNo());
					System.out.println("Total Balance: " + Info.getTotalBalance());

					break;
				} else if (x == 2) {
					List<Customer> list = session.createQuery("from Customer").list();// get all account data in list
					
					for (Customer data : list)// retrieve data from list
					{
						System.out.println("Account No: " + data.getAccNum());
						System.out.println("Name: " + data.getName());
						System.out.println("Address: " + data.getAddress());
						System.out.println("Pincode: " + data.getPincode());
						System.out.println("Mobile No: " + data.getMobNo());
						System.out.println("Total Balance: " + data.getTotalBalance());
						System.out.println("___________________");

					}
					break;
				} else {
					System.out.println("Invalid Choice");
				}
			} catch (Exception e) {
				System.out.println("Invalid Account");
			}

		}
	}

//Check balance	
	public void balanceEnquiry() {
		while (true) {
			try {
				System.out.println("Enter Account Number:");
				int AN = sc.nextInt();
				Customer Info = session.get(Customer.class, AN);// get account

				System.out.println("Enter Password");
				int pass = sc.nextInt();
				Customer check = session.get(Customer.class, pass);// get password

				System.out.println("your Balance is: " + Info.getTotalBalance());// get balance
																					
				break;
			}

			catch (Exception e) {
				System.out.println("Invalid Input");
			}
		}
	}

//Add Money to account
	public void depositMoney() {

		Transaction t = session.beginTransaction();

		while (true) {

			try {
				System.out.println("Enter Account Number:");
				int AN = sc.nextInt();
//				ac.setAccNum(AN);

				Customer customer = session.get(Customer.class, AN);

				System.out.println("Enter Amount");
				int amount = sc.nextInt();

				if (amount > 0) // amount must be greater than 0
				{

					double d = amount + customer.getTotalBalance();// get previous balance from db and add amount in it

					customer.setTotalBalance(d);

					th.setCreditedMoney(amount);

					th.setTaccNum(AN);
					
					th.setDate(thisdate);

					session.save(th);

					t.commit();

					System.out.println("Money credited successfully");

					break;
				}

			} catch (Exception e) {
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
				Customer customer = session.get(Customer.class, AN); // get account from db

				System.out.println("Enter Amount");
				double amount = sc.nextDouble();

				// amount must be greater than 0 but less than account balance
				if (amount <= customer.getTotalBalance() && amount > 0) {

					double d = customer.getTotalBalance() - amount;// amount debit from account balance

					customer.setTotalBalance(d);

					th.setDebitedMoney(amount);

					th.setTaccNum(AN);

					th.setDate(thisdate);

					session.save(th);

					session.beginTransaction().commit();

					System.out.println("Money debited successfully");

					break;
				} else {
					System.out.println("Amount should not be less than 0 or greater than Account balance");
				}
			} catch (Exception e) {
				System.out.println("Invalid data");
			}
		}
	}

//Transfer money from one account to other
	public void transferMoney() {
		Transaction_History th1;
		Transaction_History th2;

		while (true) {
			Transaction t = session.beginTransaction();
			try {

				// transfer money to
				System.out.println("Enter Account Number to Receive money ");
				int AN = sc.nextInt();
				
				Customer acc1 = session.get(Customer.class, AN);// get account from db

				// transfer money from
				System.out.println("Enter Account number to send money ");
				int AN2 = sc.nextInt();
				
				Customer acc2 = session.get(Customer.class, AN2);// get account from db

				System.out.println("Enter Amount");
				double amount = sc.nextDouble();

				// amount must greater than 0 but less than account balance
				if (amount <= acc2.getTotalBalance() && amount > 0) {

					// debit account money from which account you transfer amount
					double minus = acc2.getTotalBalance() - amount;
					
					acc2.setTotalBalance((minus));
					
					th.setDebitedMoney(amount);
					
					th.setTaccNum(AN2);
					
					th.setDate(thisdate);

					//add money to transferred account
					double add = acc1.getTotalBalance() + amount;
					
					acc1.setTotalBalance(add);

					th.setCreditedMoney(amount);
					
					th.setTaccNum(AN);
					
					th.setDate(thisdate);

					session.save(th);

					t.commit();// make transaction to db

					System.out.println("Transaction Done Successfully");

					break;

				} else {
					System.out.println("Amount should not be less than 0 or greater than Account balance");
				}
			} catch (Exception e) {
				System.out.println("Something went wrong, Please try again");
			}
		}
	}

	public void getStatements()
	{
		System.out.println("Enter account Number");
		int AN=sc.nextInt();
		
	    String q="from Transaction_History where TaccNum=:x";
	    
	    Query query = session.createQuery(q);
	    query.setParameter("x", AN);
	    
	    query.setFirstResult(0);
	    
	    query.setMaxResults(10);
	    
	    List<Transaction_History> list = query.list();
	    for(Transaction_History t:list)
	    {
	    	System.out.println("Credited Money:     "+ t.getCreditedMoney());
	    	System.out.println("Debited Money:      "+ t.getDebitedMoney());
//	    	System.out.println(t.getTaccNum());
	    	System.out.println("Transaction Date:   "+ t.getDate());
	    	System.out.println();
	    }
	}
//Close sf and Session
	public void closeConnection() {
		session.close();
		sf.close();

	}
}