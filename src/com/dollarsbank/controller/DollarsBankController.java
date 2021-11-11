package com.dollarsbank.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Queue;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dollarsbank.exception.OverdraftException;
import com.dollarsbank.model.Account;
import com.dollarsbank.model.Customer;
import com.dollarsbank.model.Transaction;
import com.dollarsbank.model.Transaction.Type;

public class DollarsBankController {

	public Customer createNewCustomer(HashMap<String, String> userLoginInfo) {
		Scanner scan = new Scanner(System.in);
		Customer creating;
		System.out.println("+------------------------------------+");
		System.out.println("|    Enter Details for New Account   |");
		System.out.println("+------------------------------------+");
		System.out.println("Customer Name:");
		String name = scan.nextLine();
		System.out.println("Customer Address:");
		String address = scan.nextLine();
		System.out.println("Contact Number:");
		String number = scan.nextLine();
		System.out.println("User Id:");
		String userID = scan.nextLine();
		while (userLoginInfo.containsKey(userID)) {
			System.out.println("User Id already in use select another:");
			userID = scan.nextLine();
		}
		System.out.println("Password:");
		String password = getValidPassword(scan);
		System.out.println("Initial Deposit:");
		double initial = scan.nextDouble();
		Account newAcc = new Account();
		creating = new Customer(name, address, number, userID, password, newAcc);
		Transaction first = new Transaction(LocalDateTime.now(), creating.getAccount().getBalance(), initial,
				Type.INITIAL_DEPOSIT);
		handleTransaction(creating, first);
		System.out.println(name+" successfully created an account");
		return creating;
	}

	public Customer login(ArrayList<Customer> customers, HashMap<String, String> userLoginInfo) {
		boolean credentials = false;
		String username = null;
		Scanner scan = new Scanner(System.in);
		System.out.println("+------------------------------------+");
		System.out.println("|         Enter Login Details        |");
		System.out.println("+------------------------------------+");
		while (!credentials) {
			System.out.println("Enter in Username:");
			username = scan.nextLine();
			System.out.println("Enter in Password:");
			String password = scan.nextLine();
			if (userLoginInfo.containsKey(username)) {
				if (userLoginInfo.get(username).equals(password))
					credentials = true;
			}
			if (!credentials)
				System.out.println("Invalid Credentials. Try again.");
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
		System.out.println("+------------------------------------+");
		System.out.println("|    Enter Details for New Deposit   |");
		System.out.println("+------------------------------------+");
		double amount = getValidDouble(scan, 0, deposit.getTransactionType());
		deposit.setamount(amount);
		handleTransaction(customer, deposit);

	}

	public void makeWithdrawl(Customer customer) {
		Transaction withdrawl = new Transaction(LocalDateTime.now(), customer.getAccount().getBalance(),
				Type.WITHDRAWL);
		Scanner scan = new Scanner(System.in);
		System.out.println("+------------------------------------+");
		System.out.println("|  Enter Details for New Withdrawl   |");
		System.out.println("+------------------------------------+");
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
		System.out.println("+------------------------------------+");
		System.out.println("|   Enter Details for New Transfer   |");
		System.out.println("+------------------------------------+");
		System.out.println("Enter User ID money is being transfered to:");
		String userID = scan.nextLine();
		while (!userLoginInfo.containsKey(userID)) {
			System.out.println("User Id does not exist try again:");
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
		System.out.println("+------------------------------------+");
		System.out.println("|        5 Recent Transaction        |");
		System.out.println("+------------------------------------+");
		Queue<Transaction> transactions = customer.getAccount().getRecentActions();
		for (int i = 0; i < transactions.size(); i++) {
			t = transactions.remove();
			transactions.add(t);
			System.out.println(t);
			System.out.println("-------------------------------------");
		}
	}

	public void displayCusomterInformation(Customer customer) {
		System.out.println("+------------------------------------+");
		System.out.println("|        Customer Information        |");
		System.out.println("+------------------------------------+");
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

	public void promptAmount(String transactionType) {
		System.out.println("Please enter in the amount that you would like to " + transactionType + ": ");
	}

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
		System.out.println("Enter in the amount you are " + verb + ": ");
		while (cond) {
			try {
				amount = scan.nextDouble();
				if (transactionType.equals(Type.WITHDRAWL) || transactionType.equals(Type.TRANSFEROUT)) {
					if (amount > highEnd)
						throw new OverdraftException(highEnd);
				}
				cond = false;
			} catch (InputMismatchException e) {
				System.out.println("Please Enter a Valid input!");
				scan.next();
			} catch (OverdraftException e) {
				System.out.println(e.getMessage());
			}
		}
		return amount;

	}

	public static int getValidIntResponse(int range, Scanner scan) {
		boolean cond = true;
		int optionHolder = -1;
		System.out.println("Enter the option you would like to use by number.");
		while (cond) {
			try {
				optionHolder = scan.nextInt();
				if (optionHolder < 1 || optionHolder > range)
					System.out.println("You must select a number between 1 and " + range + "!");
				else
					cond = false;
			} catch (InputMismatchException e) {
				System.out.println("Please enter a number!");
				scan.next();
			}
		}
		return optionHolder;
	}

	private String getValidPassword(Scanner scan) {
		boolean goodPW = false;
		Pattern pattern = Pattern.compile("^(?=.{8,})(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$");
		while (!goodPW) {
			String password = scan.nextLine();
			Matcher matcher = pattern.matcher(password);
			if (matcher.find())
				return password;
			System.out.println("Bad Password try again!");
		}
		return null;
	}
}
