package com.jsfshop.cart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.jsf.entities.ProductEntity;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@Named
@SessionScoped
public class Cart implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<CartItem> items = new ArrayList<>();

	public List<CartItem> getItems() {
		return items;
	}

	public void addToCart(ProductEntity product) {
		for (CartItem item : items) {
			if (item.getProduct().getIdProduct() == product.getIdProduct()) {
				item.setQuantity(item.getQuantity() + 1);
				return;
			}
		}
		items.add(new CartItem(product, 1));
	}

	public void removeFromCart(ProductEntity product) {
		items.removeIf(item -> item.getProduct().getIdProduct() == product.getIdProduct());
	}

	public void removeAll() {
		items.removeAll(items);
	}

	public double getTotal() {
		return items.stream().mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity()).sum();
	}
}