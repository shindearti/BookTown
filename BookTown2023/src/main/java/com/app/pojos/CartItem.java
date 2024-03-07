package com.app.pojos;

public class CartItem {

	private int id;
	private String item;
	private int qty;
	private double price, amount;
	private int supplier_id;

	public CartItem() {
		System.out.println("Cart Constructor invoked");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getSupplier_id() {
		return supplier_id;
	}

	public void setSupplier_id(int supplier_id) {
		this.supplier_id = supplier_id;
	}

	@Override
	public String toString() {
		return "CartItem [id=" + id + ", item=" + item + ", qty=" + qty + ", price=" + price + ", amount=" + amount
				+ "]";
	}

}
