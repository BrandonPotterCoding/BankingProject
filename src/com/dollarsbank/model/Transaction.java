package com.dollarsbank.model;

import java.time.LocalDateTime;

public class Transaction {

	public enum Type {WITHDRAWL, DEPOSIT, INITIAL_DEPOSIT}
	private LocalDateTime time;
	private double starting;
	private double amount;
	private Type transactionType;
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
	
	
	
	
}
