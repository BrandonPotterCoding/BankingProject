package com.dollarsbank.exception;

public class OverdraftException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public OverdraftException(double highEnd) {
		super("Overdraft Exception: Amount going out exceeded current balance of: "+highEnd);
	}

}
