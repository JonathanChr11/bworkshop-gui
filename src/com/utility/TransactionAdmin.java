package com.utility;

public class TransactionAdmin {

	private Integer TransactionID;
	private String Username, Sparepart;
	private Integer Quantity;
	
	public TransactionAdmin(Integer transactionID, String username, String sparepart, Integer quantity) {
		TransactionID = transactionID;
		Username = username;
		Sparepart = sparepart;
		Quantity = quantity;
	}

	public Integer getTransactionID() {
		return TransactionID;
	}

	public void setTransactionID(Integer transactionID) {
		TransactionID = transactionID;
	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getSparepart() {
		return Sparepart;
	}

	public void setSparepart(String sparepart) {
		Sparepart = sparepart;
	}

	public Integer getQuantity() {
		return Quantity;
	}

	public void setQuantity(Integer quantity) {
		Quantity = quantity;
	}
	
}
