package com.tech.project;

import java.util.Date;
import java.util.List;
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

	Transaction_History th = new Transaction_History();
	Date thisdate = new Date();

// create account
	public void createAccount() {
		while (true) {

			try {

				Transaction t = session.beginTransaction();// transaction strt

				System.out.println("Enter Account Number");
				ac.setAccNum(sc.nextInt());
				th.setTaccNum(ac.getAccNum());

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
						th.setDepositMoney(d);
						th.setTotalBalance(d);
						th.setDate(thisdate);
						System.out.println("Money has been deposited successfully");

						break;
					} else {
						System.out.println("Money should be greater than or equal to 500");
					}
				}

				ac.setTransaction_history(th);
//				t.commit();
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
					System.out.println(Info);// read account info
					System.out.println("Debited Money: " + Info.getTransaction_history().getDebitedMoney());
					System.out.println("Deposited Money: " + Info.getTransaction_history().getDepositMoney());
					System.out.println("Available Total Balance: " + Info.getTransaction_history().getTotalBalance());
					System.out.println("Last Transaction Date: " + Info.getTransaction_history().getDate());
					break;
				} else if (x == 2) {
					List<Customer> list = session.createQuery("from Customer").list();// get all account data in list
					for (Customer data : list)// retrieve data from list
					{
						System.out.println(data.getAccNum());
						System.out.println(data.getName());
						System.out.println(data.getMobNo());
						System.out.println(data.getAddress());
						System.out.println(data.getPincode());
						System.out.println(data.getPassword());
						System.out.println("Debited Money: " + data.getTransaction_history().getDebitedMoney());
						System.out.println("Deposited Money: " + data.getTransaction_history().getDepositMoney());
						System.out
								.println("Available Total Balance: " + data.getTransaction_history().getTotalBalance());
						System.out.println("Last Transaction Date: " + data.getTransaction_history().getDate());
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

				System.out.println("your Balance is: " + Info.getTransaction_history().getTotalBalance());// read
																											// balance
				break;
			}

			catch (Exception e) {
				System.out.println("Invalid Input");
			}
		}
	}

//Add Money to account
	public void depositMoney() {
		while (true) {

			try {
				System.out.println("Enter Account Number:");
				int AN = sc.nextInt();

				Customer customer = session.get(Customer.class, AN);
				Transaction_History Info = session.get(Transaction_History.class, AN);// get account from db
//				int taccNum = Info.getTransaction_history().getTaccNum();

				System.out.println("Enter Amount");
				int amount = sc.nextInt();

				if (amount > 0) // amount must be greater than 0
				{

//	    	double dd=Info.getDepositMoney();
//	    	double d=amount + dd;
//	    	or

					double d = amount + Info.getTotalBalance();// get previous balance from db and add amount in it

					Info.setDepositMoney((amount));
					Info.setTotalBalance(d);
					// update new added money to account

					customer.setTransaction_history(Info);
//			     	session.save(ac);
//			     	session.save(th);

					Info.setDate(thisdate);
					session.beginTransaction().commit(); // make transaction to db

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
//                 Transaction t=session.beginTransaction();
			try {
				System.out.println("Enter Account Number:");
				int AN = sc.nextInt();
				Customer customer = session.get(Customer.class, AN); // get account from db
				Transaction_History Info = session.get(Transaction_History.class, AN);

				System.out.println("Enter Amount");
				double amount = sc.nextDouble();
//				th.setDebitedMoney(sc.nextInt());

				// amount must be greater than 0 but less than account balance
				if (amount <= Info.getTotalBalance() && amount > 0) {

					double d = Info.getTotalBalance() - amount;// amount debit from account balance

//                    th.setDebitedMoney(amount);
					Info.setDebitedMoney(amount);
					Info.setTotalBalance(d);// update debited money to accounts
//					Info.setDepositMoney(d);

					customer.setTransaction_history(Info);

//                     session.save(ac);
//                     session.save(Info);
					Info.setDate(thisdate);
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

		while (true) {
			Transaction t = session.beginTransaction();
			try {

				// transfer money to
				System.out.println("Enter account number in which you want to transfer money ");
				int AN = sc.nextInt();
				Customer Transfer = session.get(Customer.class, AN);// get account from db
				Transaction_History transfer_to = session.get(Transaction_History.class, AN);

				// transfer money from
				System.out.println("Enter Account number from which you want to transfer money ");
				int AN2 = sc.nextInt();
				Customer User = session.get(Customer.class, AN);// get account from db

				Transaction_History transfer_from = session.get(Transaction_History.class, AN2);

				System.out.println("Enter Amount");
				double amount = sc.nextDouble();

				// amount must greater than 0 but less than account balance
				if (amount <= transfer_from.getTotalBalance() && amount > 0) {

					// add money to transferred account
					double transfer = transfer_to.getTotalBalance() + amount;
					transfer_to.setTotalBalance(transfer);
					transfer_to.setDepositMoney(amount);

					// debit account money from which account you transfer amount
					double input = transfer_from.getTotalBalance() - amount;
					transfer_from.setTotalBalance((input));
					transfer_from.setDebitedMoney(amount);

					Transfer.setTransaction_history(transfer_to);
					User.setTransaction_history(transfer_from);
//                    session.save(ac);
					transfer_to.setDate(thisdate);
					transfer_from.setDate(thisdate);
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

//Close sf and Session
	public void closeConnection() {
		session.close();
		sf.close();

	}
}
