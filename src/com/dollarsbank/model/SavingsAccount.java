package com.dollarsbank.model;

import java.util.Queue;

public class SavingsAccount extends Account{

	public SavingsAccount(double balance, Queue<Transaction> recentActions) {
		super(balance, recentActions);
	}

}
