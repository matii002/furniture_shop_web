package com.jsfshop.orderProduct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jsf.dao.OrderProductDAO;
import com.jsf.entities.OrderDetailsEntity;
import com.jsf.entities.OrderProductEntity;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.Flash;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@RequestScoped
public class OrderProductList {

	private static final String PAGE_ORDER_PRODUCT_EDIT = "orderProductEdit?faces-redirect=true";
	private static final String PAGE_ORDER_DETAILS = "orderDetails?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private String id;

	@Inject
	ExternalContext extcontext;

	@Inject
	Flash flash;

	@EJB
	OrderProductDAO orderProductDAO;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<OrderProductEntity> getFullList() {
		return orderProductDAO.getFullList();
	}

	public List<OrderProductEntity> getList() {
		List<OrderProductEntity> list = null;

		Map<String, Object> searchParams = new HashMap<String, Object>();

		if (id != null && id.length() > 0) {
			searchParams.put("id", id);
		}

		list = orderProductDAO.getList(searchParams);

		return list;
	}

	public String newOrderProduct() {
		OrderProductEntity order = new OrderProductEntity();
		flash.put("order", order);

		return PAGE_ORDER_PRODUCT_EDIT;
	}

	public String editOrderProduct(OrderProductEntity orderProduct) {

		flash.put("orderProduct", orderProduct);

		return "orderEdit?faces-redirect=true";
	}

	public String showOrderDetails(OrderProductEntity orderProduct) {

		flash.put("orderProduct", orderProduct);

		return PAGE_ORDER_DETAILS;
	}
	
	public String deleteOrderProduct(OrderProductEntity orderProduct) {
		orderProductDAO.remove(orderProduct);
		return PAGE_STAY_AT_THE_SAME;
	}
}
