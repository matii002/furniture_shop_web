package com.jsfshop.menuNavigation;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class MenuNavigation {
	private static final String PAGE_PRODUCT_LIST = "/pages/shop-assistant/productList?faces-redirect=true";
	private static final String PAGE_REGISTRATION = "/pages/registrationPage?faces-redirect=true";
	private static final String PAGE_LOGIN = "/pages/loginPage?faces-redirect=true";
	private static final String PAGE_ORDER_PRODUCT = "/pages/shop-assistant/orderProductList?faces-redirect=true";
	private static final String PAGE_USER = "/pages/admin/userList?faces-redirect=true";
	private static final String PAGE_PRODUCT_CARD = "/pages/user/productCard?faces-redirect=true";
	
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
