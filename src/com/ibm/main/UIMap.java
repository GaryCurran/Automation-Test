
package com.ibm.main;

/**
 * @author Gary Curran  * 
 *         UI Map for 0bject identifiers for Automation
 * 
 *         July 2017
 * 
 */

public class UIMap {

	// ********** UI Elements ***********************//
	public static String userNameTextBox = "input[name='username']";
	public static String clientTextBox = "input[name='txtclientix']";
	public static String passwordTextBox = "input[name='txtpassword']";
	public static String btnLogin = "input[name='btnLogin']";
	public static String btnCancel = "input[name='btncancel']";
	public static String btnSave = "input[name='btnsave']";
	public static String clientNameText = "input[name='txtclientname']"; 
	public static String btnLogOff = "span[title='Log Off']";
	
	
	// ********** NavBar Elements ***********************//
	public static String envirnoment = "select[name='sltEnv']";
	public static String admin = "div[id='el2']";
	public static String manageClients = "Manage clients";
	public static String addNewClient = "#divAction > table > tbody > tr:nth-child(4) > td:nth-child(1) > span";

	
	// ********** Frames Elements ***********************//
	public static String mainFrame = "#main";
	public static String navFrame = "#nav";

	
	// ********** Window Elements ***********************//
	public static String addNewClientWindow = "Add new client";

}
