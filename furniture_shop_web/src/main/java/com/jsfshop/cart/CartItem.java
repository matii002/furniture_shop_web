package com.jsfshop.cart;

import com.jsf.entities.ProductEntity;

public class CartItem {

	public CartItem(ProductEntity product, int quantity) {
		this.product = product;
		this.quantity = quantity;
	}

	private ProductEntity product;

	public ProductEntity getProduct() {
		return product;
	}

	public void setProduct(ProductEntity product) {
		this.product = product;
	}

	private int quantity;

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
