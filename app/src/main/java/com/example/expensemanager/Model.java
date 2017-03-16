package com.example.expensemanager;

public class Model {

	String id;
	double amount;
	String category;
	String description;
	int date;
	int month;
	int year;
	String mode;
	public Model()
	{
		
	}
	public Model(String id, double amount, String category, String description,
			int date, int month, int year, String mode) {
		super();
		this.id = id;
		this.amount = amount;
		this.category = category;
		this.description = description;
		this.date = date;
		this.month = month;
		this.year = year;
		this.mode = mode;
	}
	public String getId() {
		return id;
	}
	public double getAmount() {
		return amount;
	}
	public String getCategory() {
		return category;
	}
	public String getDescription() {
		return description;
	}
	public int getDate() {
		return date;
	}
	public int getMonth() {
		return month;
	}
	public int getYear() {
		return year;
	}
	public String getMode() {
		return mode;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setDate(int date) {
		this.date = date;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	
}
