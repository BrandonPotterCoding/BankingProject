package com.dollarsbank.model;

public class Customer {

	private String name;
	private String address;
	private String number;
	private String login;
	private String password;
	private Account account;
	public Customer() {
		this("", "", "", "", "", null);
	}
	public Customer(String name, String address, String number, String login, String password, Account account) {
		super();
		this.name = name;
		this.address = address;
		this.number = number;
		this.login = login;
		this.password = password;
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
}
