package com.dollarsbank.model;

import java.time.LocalDateTime;

public class Transaction {

	public enum Type {
		WITHDRAWL, DEPOSIT, INITIAL_DEPOSIT, TRANSFEROUT, TRANSFERIN
	}

	private LocalDateTime time;
	private double starting;
	private double amount;
	private Type transactionType;

	public Transaction() {
		this(null, 0, 0, null);
	}
	public Transaction(LocalDateTime time, double starting, Type transactionType) {
		super();
		this.time=time;
		this.starting = starting;
		this.transactionType = transactionType;
	}

	public Transaction(LocalDateTime time, double starting, double amount, Type transactionType) {
		super();
		this.time = time;
		this.starting = starting;
		this.amount = amount;
		this.transactionType = transactionType;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public double getStarting() {
		return starting;
	}

	public void setStarting(double starting) {
		this.starting = starting;
	}

	public double getamount() {
		return amount;
	}

	public void setamount(double amount) {
		this.amount = amount;
	}

	public Type getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(Type transactionType) {
		this.transactionType = transactionType;
	}

	@Override
	public String toString() {
		return "Transaction: Type - "+transactionType+ "\nStarting Amount: "+starting+"\nFinal Amount: "+finalAmount()+"\nTime of Transaction: "+time;
	}

	private double finalAmount() {
		switch (transactionType) {
		case WITHDRAWL:
			return starting-amount;
		case DEPOSIT:
			return starting+amount;
		case INITIAL_DEPOSIT:
			return starting+amount;
		case TRANSFEROUT:
			return starting-amount;
		case TRANSFERIN:
			return starting+amount;
		default:
			return 0.0;
		}
	}

}
