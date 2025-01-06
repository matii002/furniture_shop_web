package com.jsfshop.menuNavigation;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class MenuNavigation {
	private static final String PAGE_PRODUCT_LIST = "productList?faces-redirect=true";
	private static final String PAGE_REGISTRATION = "registrationPage?faces-redirect=true";
	private static final String PAGE_LOGIN = "loginPage?faces-redirect=true";
	private static final String PAGE_ORDER_PRODUCT = "orderProductList?faces-redirect=true";
	private static final String PAGE_USER = "userList?faces-redirect=true";
	private static final String PAGE_PRODUCT_CARD = "productCard?faces-redirect=true";
	
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
		return PAGE_ORDER_PRODUCT;
	}
	
	public String goToUsers() {
		return PAGE_USER;
	}
	
	public String goToProductsCards() {
		return PAGE_PRODUCT_CARD;
	}
}
