package com.utility;

public class TransactionUser {

	private int TransactionID;
	private String SparePartName;
	private String Brand;
	private int Stock;
	private int Price;
	
	public TransactionUser(int transactionID, String sparePartName, String brand, int stock, int price) {
		TransactionID = transactionID;
		SparePartName = sparePartName;
		Brand = brand;
		Stock = stock;
		Price = price;
	}

	public int getTransactionID() {
		return TransactionID;
	}

	public void setTransactionID(int transactionID) {
		TransactionID = transactionID;
	}

	public String getSparePartName() {
		return SparePartName;
	}

	public void setSparePartName(String sparePartName) {
		SparePartName = sparePartName;
	}

	public String getBrand() {
		return Brand;
	}

	public void setBrand(String brand) {
		Brand = brand;
	}

	public int getStock() {
		return Stock;
	}

	public void setStock(int stock) {
		Stock = stock;
	}

	public int getPrice() {
		return Price;
	}

	public void setPrice(int price) {
		Price = price;
	}
	
}
