package com.jsfshop.menuNavigation;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class MenuNavigation {
	private static final String PAGE_PRODUCT_LIST = "/pages/shop-assistant/productList?faces-redirect=true";
	private static final String PAGE_REGISTRATION = "/public/registrationPage?faces-redirect=true";
	private static final String PAGE_LOGIN = "/public/loginPage?faces-redirect=true";
	private static final String PAGE_ORDER = "/pages/shop-assistant/orderList?faces-redirect=true";
	private static final String PAGE_USER = "/pages/admin/userList?faces-redirect=true";
	private static final String PAGE_PRODUCT_CARD = "/public/productCard?faces-redirect=true";
	private static final String PAGE_REALIZATIONS = "/public/realizationsPage?faces-redirect=true";
	private static final String PAGE_CART = "/pages/user/cart?faces-redirect=true";

	public String goToProductsList() {
		return PAGE_PRODUCT_LIST;
	}

	public String goToRegistration() {
		return PAGE_REGISTRATION;
	}

	public String goToLogin() {
		return PAGE_LOGIN;
	}

	public String goToOrdersProducts() {
		return PAGE_ORDER;
	}

	public String goToUsers() {
		return PAGE_USER;
	}

	public String goToProductsCards() {
		return PAGE_PRODUCT_CARD;
	}

	public String goToRealizations() {
		return PAGE_REALIZATIONS;
	}

	public String goToCart() {
		return PAGE_CART;
	}
}
