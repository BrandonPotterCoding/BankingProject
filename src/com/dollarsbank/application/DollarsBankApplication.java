package com.dollarsbank.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.dollarsbank.controller.DollarsBankController;
import com.dollarsbank.model.Customer;

public class DollarsBankApplication {

	public static void main(String args[]) {
		DollarsBankController controller = new DollarsBankController();
		ArrayList<Customer> customers = new ArrayList<Customer>();
		Customer currentCustomer = null;
		HashMap<String, String> userLoginInfo = new HashMap<String, String>();
		Scanner scan = new Scanner(System.in);
		int welcomeChoice = 0;
		int loggedInChoice = 0;
		while (welcomeChoice != 3) {
			listOriginalOptions();
			welcomeChoice = DollarsBankController.getValidIntResponse(3, scan);
			switch (welcomeChoice) {
			case 1:
				Customer newCustomer = controller.createNewCustomer(userLoginInfo);
				userLoginInfo.put(newCustomer.getLogin(), newCustomer.getPassword());
				customers.add(newCustomer);
				break;
			case 2:
				currentCustomer = controller.login(customers, userLoginInfo);
				break;
			case 3:
				System.out.println("Thank you for banking at DOLLARS BANK!");
				break;
			default:
				System.out.println("error in welcome switch case");
			}
			while (currentCustomer != null) {
				listLoggedInOptions();
				loggedInChoice = DollarsBankController.getValidIntResponse(6, scan);
				switch (loggedInChoice) {
				case 1:
					controller.makeDeposit(currentCustomer);
					break;
				case 2:
					controller.makeWithdrawl(currentCustomer);
					break;
				case 3:
					controller.transferFunds(currentCustomer, userLoginInfo, customers);
					break;
				case 4:
					controller.displayRecentTransactions(currentCustomer);
					break;
				case 5:
					controller.displayCusomterInformation(currentCustomer);
					break;
				case 6:
					currentCustomer=null;
					System.out.println("Logged out!");
					break;
				default:
					System.out.println("error in logged in switch case");
				}
			}

		}

	}

	private static void listOriginalOptions() {
		System.out.println("+------------------------------------+");
		System.out.println("|       Welcome to Dollars Bank      |");
		System.out.println("+------------------------------------+");
		System.out.println("1. Create New Account");
		System.out.println("2. Login");
		System.out.println("3. Exit.");
	}

	private static void listLoggedInOptions() {
		System.out.println("+------------------------------------+");
		System.out.println("|          Welcome Cusomter          |");
		System.out.println("+------------------------------------+");
		System.out.println("1. Deposit Amount");
		System.out.println("2. Withdraw Amount");
		System.out.println("3. Funds Transfer");
		System.out.println("4. View 5 Recent Transactions");
		System.out.println("5. Display Customer Information");
		System.out.println("6. Sign out");
	}

}
