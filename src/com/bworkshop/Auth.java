package com.bworkshop;

import javafx.stage.Stage;

public class Auth extends Stage{
	
	Login log;
	Register reg;
	MainAdmin mainAdm;
	MainCustomer mainCust;
	BuySparepartMenu buySpare;
	UserTableView usrTableView;
	ManageSparepartMenu mgSparepart;
	ManageUser mgUser;
	ViewTransactionAdmin viewTfAdmin;
	
	public Auth() {
		displayLogin();
	}
	
	public void displayLogin() {
		if (log == null) {
			log = new Login();
		}
		this.setScene(log.getLogin());
	}
	
	public void displayRegister() {
		if (reg == null) {
			reg = new Register();
		}
		this.setScene(reg.getRegister());
	}
	
	
	public void displayMainAdmin() {
		if (mainAdm == null) {
			mainAdm = new MainAdmin();
		}
		this.setScene(mainAdm.getMainAdmin());
	}
	
	public void displayMainCustomer() {
		if (mainCust == null) {
			mainCust = new MainCustomer();
		}
		this.setScene(mainCust.getMainCustomer());
	}
	
	public void displayBuySparepartMenu() {
		if (buySpare == null) {
			buySpare = new BuySparepartMenu();
		}
		this.setScene(buySpare.getBuySparepartMenu());
	}
	
	public void displayUserTableView() {
		if (usrTableView == null) {
			usrTableView = new UserTableView();
		}
		this.setScene(usrTableView.getUserTableView());
	}
	
	public void displayManageSparepartMenu() {
		if (mgSparepart == null) {
			mgSparepart = new ManageSparepartMenu();
		}
		this.setScene(mgSparepart.getManageSparepartMenu());
	}
	
	public void displayManageUser() {
		if (mgUser == null) {
			mgUser = new ManageUser();
		}
		this.setScene(mgUser.getManageUser());
	}
	
	public void displayViewTransactionAdmin() {
		if (viewTfAdmin == null) {
			viewTfAdmin = new ViewTransactionAdmin();
		}
		this.setScene(viewTfAdmin.getViewTransactionAdmin());
	}
}
