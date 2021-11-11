package com.dollarsbank.exception;

public class NegativeValueException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NegativeValueException() {
		super("No negative values accepted!");
	}

}
