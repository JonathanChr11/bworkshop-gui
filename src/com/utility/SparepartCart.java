package com.utility;

public class SparepartCart {

	private String SparePartName;
	private String Brand;
	private int Quantity;
	private int Price;
	private int Total;
	private int UserID;
	
	public SparepartCart(String sparePartName, String brand, int quantity, int price, int total, int userID) {
		SparePartName = sparePartName;
		Brand = brand;
		Quantity = quantity;
		Price = price;
		Total = total;
		UserID = userID;
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

	public int getQuantity() {
		return Quantity;
	}

	public void setQuantity(int quantity) {
		Quantity = quantity;
	}

	public int getPrice() {
		return Price;
	}

	public void setPrice(int price) {
		Price = price;
	}

	public int getTotal() {
		return Total;
	}

	public void setTotal(int total) {
		Total = total;
	}

	public int getUserID() {
		return UserID;
	}

	public void setUserID(int userID) {
		UserID = userID;
	}
	
}
