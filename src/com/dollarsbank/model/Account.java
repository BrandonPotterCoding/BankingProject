package com.dollarsbank.model;

import java.util.Queue;

public class Account {

	private double balance;
	private Queue<Transaction> recentActions;
	
	public Account(double balance, Queue<Transaction> recentActions) {
		super();
		this.balance = balance;
		this.recentActions = recentActions;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public Queue<Transaction> getRecentActions() {
		return recentActions;
	}
	public void setRecentActions(Queue<Transaction> recentActions) {
		while(recentActions.size()>5)
			recentActions.remove();
		this.recentActions = recentActions;
	}
	public void addRecentAction(Transaction action) {
		recentActions.add(action);
		if(recentActions.size()>5)
			recentActions.remove();
	}
	@Override
	public String toString() {
		return "Account:\nBalance=" + balance;
	}
	
	
}
