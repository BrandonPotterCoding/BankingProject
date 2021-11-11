package com.dollarsbank.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import com.dollarsbank.model.Customer;
import com.dollarsbank.model.Transaction;
import com.dollarsbank.model.Transaction.Type;

public class DollarsBankController {

	public Customer createNewCustomer() {
		Scanner scan = new Scanner(System.in);
		Customer creating = new Customer();
		System.out.println("+------------------------------------+");
		System.out.println("|	 Enter Details for New Account   |");
		System.out.println("+------------------------------------+");
		
		return creating;
	}
	public Customer login(ArrayList<Customer> customer) {
		Customer found = null;
		Scanner scan = new Scanner(System.in);
		System.out.println("+------------------------------------+");
		System.out.println("|	  	  Enter Login Details  	     |");
		System.out.println("+------------------------------------+");
		
		
		
		
		return found;
	}
	public void makeDeposit(Customer customer) {
		Transaction deposit = new Transaction();
		Scanner scan = new Scanner(System.in);
		System.out.println("+------------------------------------+");
		System.out.println("|	 Enter Details for New Deposit   |");
		System.out.println("+------------------------------------+");
		
		
	}
	public void makeWithdrawl(Customer customer) {
		Transaction withdrawl = new Transaction(LocalDateTime.now(),customer.getAccount().getBalance(),Type.TRANSFERIN);
		Scanner scan = new Scanner(System.in);
		System.out.println("+------------------------------------+");
		System.out.println("|  Enter Details for New Withdrawl   |");
		System.out.println("+------------------------------------+");
		
		
	}
	//return transaction to add to the sender, take care of the receiver transaction in method
	public void transferFunds(Customer receiver,Customer sender) {
		Transaction transferIn = new Transaction(LocalDateTime.now(),receiver.getAccount().getBalance(),Type.TRANSFERIN);
		Transaction transferOut = new Transaction(LocalDateTime.now(),sender.getAccount().getBalance(),Type.TRANSFEROUT);
		Scanner scan = new Scanner(System.in);
		System.out.println("+------------------------------------+");
		System.out.println("|	Enter Details for New Transfer   |");
		System.out.println("+------------------------------------+");
		
		
	}
	public void displayRecentTransactions(Customer customer) {
		System.out.println("+------------------------------------+");
		System.out.println("|		 5 Recent Transaction	     |");
		System.out.println("+------------------------------------+");
		
	}
	public void displayCusomterInformation(Customer cusomter) {
		System.out.println("+------------------------------------+");
		System.out.println("|	 	 Customer Information 	     |");
		System.out.println("+------------------------------------+");
	}
	public void handleTransaction(Customer customer, Transaction action) {
		
	}
	
	public void promptAmount(String transactionType) {
		System.out.println("Please enter in the amount that you would like to "+transactionType+": ");
	}
}

















