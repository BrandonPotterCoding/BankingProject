package com.dollarsbank.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Queue;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dollarsbank.exception.NegativeValueException;
import com.dollarsbank.exception.OverdraftException;
import com.dollarsbank.utility.ColorsUtility;
import com.dollarsbank.model.Account;
import com.dollarsbank.model.Customer;
import com.dollarsbank.model.Transaction;
import com.dollarsbank.model.Transaction.Type;

public class DollarsBankController {

	public Customer createNewCustomer(HashMap<String, String> userLoginInfo) {
		Scanner scan = new Scanner(System.in);
		Customer creating;
		System.out.println(ColorsUtility.BLUE+"+------------------------------------+");
		System.out.println("|    Enter Details for New Account   |");
		System.out.println("+------------------------------------+"+ColorsUtility.RESET);
		System.out.println("Customer Name:"+ColorsUtility.CYAN);
		String name = scan.nextLine();
		System.out.println(ColorsUtility.RESET+"Customer Address:"+ColorsUtility.CYAN);
		String address = scan.nextLine();
		System.out.println(ColorsUtility.RESET+"Contact Number:"+ColorsUtility.CYAN);
		String number = scan.nextLine();
		System.out.println(ColorsUtility.RESET+"User Id:"+ColorsUtility.CYAN);
		String userID = scan.nextLine();
		while (userLoginInfo.containsKey(userID)) {
			System.out.println(ColorsUtility.RESET+"User Id already in use select another:"+ColorsUtility.CYAN);
			userID = scan.nextLine();
		}
		System.out.println(ColorsUtility.RESET+"Password:"+ColorsUtility.CYAN);
		String password = getValidPassword(scan);
		System.out.println(ColorsUtility.RESET+"Initial Deposit:"+ColorsUtility.CYAN);
		double initial = getDouble(scan);
		Account newAcc = new Account();
		creating = new Customer(name, address, number, userID, password, newAcc);
		Transaction first = new Transaction(LocalDateTime.now(), creating.getAccount().getBalance(), initial,
				Type.INITIAL_DEPOSIT);
		handleTransaction(creating, first);
		System.out.println(ColorsUtility.GREEN+name+" successfully created an account");
		return creating;
	}

	public Customer login(ArrayList<Customer> customers, HashMap<String, String> userLoginInfo) {
		boolean credentials = false;
		String username = null;
		Scanner scan = new Scanner(System.in);
		System.out.println(ColorsUtility.BLUE+"+------------------------------------+");
		System.out.println("|         Enter Login Details        |");
		System.out.println("+------------------------------------+"+ColorsUtility.RESET);
		while (!credentials) {
			System.out.println(ColorsUtility.RESET+"Enter in Username:"+ColorsUtility.CYAN);
			username = scan.nextLine();
			System.out.println(ColorsUtility.RESET+"Enter in Password:"+ColorsUtility.CYAN);
			String password = scan.nextLine();
			if (userLoginInfo.containsKey(username)) {
				if (userLoginInfo.get(username).equals(password))
					credentials = true;
			}
			if (!credentials)
				System.out.println(ColorsUtility.RED+"Invalid Credentials. Try again."+ColorsUtility.CYAN);
		}
		for (Customer customer : customers) {
			if (customer.getLogin().equals(username)) {
				return customer;
			}
				
		}
		return null;
	}

	public void makeDeposit(Customer customer) {
		Transaction deposit = new Transaction(LocalDateTime.now(), customer.getAccount().getBalance(), Type.DEPOSIT);
		Scanner scan = new Scanner(System.in);
		System.out.println(ColorsUtility.BLUE+"+------------------------------------+");
		System.out.println("|    Enter Details for New Deposit   |");
		System.out.println("+------------------------------------+"+ColorsUtility.CYAN);
		double amount = getValidDouble(scan, 0, deposit.getTransactionType());
		deposit.setamount(amount);
		handleTransaction(customer, deposit);

	}

	public void makeWithdrawl(Customer customer) {
		Transaction withdrawl = new Transaction(LocalDateTime.now(), customer.getAccount().getBalance(),
				Type.WITHDRAWL);
		Scanner scan = new Scanner(System.in);
		System.out.println(ColorsUtility.BLUE+"+------------------------------------+");
		System.out.println("|  Enter Details for New Withdrawl   |");
		System.out.println("+------------------------------------+"+ColorsUtility.CYAN);
		double amount = getValidDouble(scan, customer.getAccount().getBalance(), Type.WITHDRAWL);
		withdrawl.setamount(amount);
		handleTransaction(customer, withdrawl);

	}

	public void transferFunds(Customer sender, HashMap<String, String> userLoginInfo,
			ArrayList<Customer> customerList) {
		Customer receiver = null;
		Transaction transferOut = new Transaction(LocalDateTime.now(), sender.getAccount().getBalance(),
				Type.TRANSFEROUT);
		Scanner scan = new Scanner(System.in);
		System.out.println(ColorsUtility.BLUE+"+------------------------------------+");
		System.out.println("|   Enter Details for New Transfer   |");
		System.out.println("+------------------------------------+"+ColorsUtility.RESET);
		System.out.println("Enter User ID money is being transfered to:"+ColorsUtility.CYAN);
		String userID = scan.nextLine();
		while (!userLoginInfo.containsKey(userID)) {
			System.out.println(ColorsUtility.RED+"User Id does not exist try again:"+ColorsUtility.CYAN);
			userID = scan.nextLine();
		}
		for (Customer customer : customerList) {
			if (customer.getLogin().equals(userID)) {
				receiver = customer;
			}
		}
		Transaction transferIn = new Transaction(LocalDateTime.now(), receiver.getAccount().getBalance(),
				Type.TRANSFERIN);
		double amount = getValidDouble(scan, sender.getAccount().getBalance(), Type.TRANSFEROUT);
		transferIn.setamount(amount);
		transferOut.setamount(amount);
		handleTransaction(sender, transferOut);
		handleTransaction(receiver, transferIn);

	}

	public void displayRecentTransactions(Customer customer) {
		Transaction t;
		System.out.println(ColorsUtility.BLUE+"+------------------------------------+");
		System.out.println("|        5 Recent Transaction        |");
		System.out.println("+------------------------------------+"+ColorsUtility.RESET);
		Queue<Transaction> transactions = customer.getAccount().getRecentActions();
		for (int i = 0; i < transactions.size(); i++) {
			t = transactions.remove();
			transactions.add(t);
			System.out.println(t);
			System.out.println(ColorsUtility.BLUE+"-------------------------------------"+ColorsUtility.RESET);
		}
	}

	public void displayCusomterInformation(Customer customer) {
		System.out.println(ColorsUtility.BLUE+"+------------------------------------+");
		System.out.println("|        Customer Information        |");
		System.out.println("+------------------------------------+"+ColorsUtility.RESET);
		System.out.println(customer);
	}

	public void handleTransaction(Customer customer, Transaction action) {
		Account a = customer.getAccount();
		switch (action.getTransactionType()) {
		case WITHDRAWL:
		case TRANSFEROUT:
			a.setBalance(a.getBalance() - action.getamount());
			break;
		case DEPOSIT:
		case INITIAL_DEPOSIT:
		case TRANSFERIN:
			a.setBalance(a.getBalance() + action.getamount());
			break;

		}
		a.addRecentAction(action);
	}
//
//	public void promptAmount(String transactionType) {
//		System.out.println("Please enter in the amount that you would like to " + transactionType + ": ");
//	}

	public double getValidDouble(Scanner scan, double highEnd, Type transactionType) {
		boolean cond = true;
		String verb;
		double amount = 0.0;
		switch (transactionType) {
		case WITHDRAWL:
			verb = "withdrawling";
			break;
		case DEPOSIT:
			verb = "depositing";
			break;
		case TRANSFEROUT:
			verb = "transfering";
			break;
		default:
			verb = "failed";
		}
		System.out.println(ColorsUtility.RESET+"Enter in the amount you are " + verb + ": "+ColorsUtility.CYAN);
		while (cond) {
			try {
				amount = getDouble(scan);
				if (transactionType.equals(Type.WITHDRAWL) || transactionType.equals(Type.TRANSFEROUT)) {
					if (amount > highEnd)
						throw new OverdraftException(highEnd);
				}
				cond = false;
			} catch (InputMismatchException e) {
				System.out.println(ColorsUtility.RED+"Please Enter a Valid input!"+ColorsUtility.CYAN);
				scan.next();
			} catch (OverdraftException e) {
				System.out.println(ColorsUtility.RED+e.getMessage()+ColorsUtility.CYAN);
				System.out.println(ColorsUtility.RESET+"Enter in the amount you are " + verb + ": "+ColorsUtility.CYAN);
			}
		}
		return amount;

	}

	private double getDouble(Scanner scan) {
		boolean cond = true;
		while(cond) {
			try {
				double amount = scan.nextDouble();
				if(amount<0)
					throw new NegativeValueException();
				return amount;
			}catch(InputMismatchException e) {
				System.out.println(ColorsUtility.RED+"Please Enter a number!"+ColorsUtility.CYAN);
				scan.next();
			} catch (NegativeValueException e) {
				System.out.println(ColorsUtility.RED+e.getMessage()+ColorsUtility.CYAN);;
			}
		}			
		return 0;
	}

	public static int getValidIntResponse(int range, Scanner scan) {
		boolean cond = true;
		int optionHolder = -1;
		System.out.println(ColorsUtility.YELLOW+"Enter the option you would like to use by number."+ColorsUtility.GREEN);
		while (cond) {
			try {
				optionHolder = scan.nextInt();
				if (optionHolder < 1 || optionHolder > range)
					System.out.println(ColorsUtility.RED+"You must select a number between 1 and " + range + "!"+ColorsUtility.GREEN);
				else
					cond = false;
			} catch (InputMismatchException e) {
				System.out.println(ColorsUtility.RED+"Please enter a number!"+ColorsUtility.GREEN);
				scan.next();
			}
		}
		return optionHolder;
	}

	private String getValidPassword(Scanner scan) {
		boolean goodPW = false;
		Pattern pattern = Pattern.compile("^(?=.{8,})(?=.*[a-z])(?=.*[A-Z])(?=.*[()*;'+@#$%^&+=-]).*$");
		while (!goodPW) {
			String password = scan.nextLine();
			Matcher matcher = pattern.matcher(password);
			if (matcher.find())
				return password;
			System.out.println(ColorsUtility.RED+"Bad Password try again!"+ColorsUtility.CYAN);
		}
		return null;
	}
}
