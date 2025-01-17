package com.jsfshop.order;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jsf.dao.OrderDetailsDAO;
import com.jsf.entities.OrderDetailsEntity;

import jakarta.ejb.EJB;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.Flash;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ViewScoped
public class OrderList implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_ORDER_EDIT = "/pages/shop-assistant/orderEdit?faces-redirect=true";

	private static final String PAGE_ORDER_DETAILS = "/pages/shop-assistant/orderDetails?faces-redirect=true";

	private static final String PAGE_STAY_AT_THE_SAME = null;

	private int id;

	@Inject
	ExternalContext extcontext;

	@Inject
	Flash flash;

	@EJB
	OrderDetailsDAO orderDetailsDAO;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<OrderDetailsEntity> getFullList() {
		return orderDetailsDAO.getFullList();
	}

	public List<OrderDetailsEntity> getList() {
		List<OrderDetailsEntity> list = null;

		Map<String, Object> searchParams = new HashMap<String, Object>();

		if (id > 0) {
			searchParams.put("id", id);
		}

		list = orderDetailsDAO.getList(searchParams);

		return list;
	}

	public String newOrder() {
		OrderDetailsEntity order = new OrderDetailsEntity();

		flash.put("order", order);

		return PAGE_ORDER_EDIT;
	}

	public String editOrder(OrderDetailsEntity order) {

		flash.put("order", order);

		return PAGE_ORDER_EDIT;
	}

	public String showOrderDetails(OrderDetailsEntity order) {

		flash.put("order", order);

		return PAGE_ORDER_DETAILS;
	}

	public String deleteOrder(OrderDetailsEntity order) {
		orderDetailsDAO.remove(order);
		return PAGE_STAY_AT_THE_SAME;
	}
}
