package com.utility;

public class Sparepart {

	private String SparePartID;
	private String SparePartName;
	private String Brand;
	private int Price;
	private int Stock;
	
	public Sparepart(String sparePartID, String sparePartName, String brand, int price, int stock) {
		SparePartID = sparePartID;
		SparePartName = sparePartName;
		Brand = brand;
		Price = price;
		Stock = stock;
	}

	public String getSparePartID() {
		return SparePartID;
	}

	public void setSparePartID(String sparePartID) {
		SparePartID = sparePartID;
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

	public int getPrice() {
		return Price;
	}

	public void setPrice(int price) {
		Price = price;
	}

	public int getStock() {
		return Stock;
	}

	public void setStock(int stock) {
		Stock = stock;
	}
	
}
