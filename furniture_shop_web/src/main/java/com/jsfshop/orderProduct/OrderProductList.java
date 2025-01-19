package com.jsfshop.orderProduct;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jsf.dao.OrderProductDAO;
import com.jsf.dao.UserDAO;
import com.jsf.entities.OrderDetailsEntity;
import com.jsf.entities.OrderProductEntity;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ViewScoped
public class OrderProductList implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String PAGE_STAY_AT_THE_SAME = null;
	private static final String PAGE_ORDER_LIST = "/pages/shop-assistant/orderList?faces-redirect=true";
	private OrderDetailsEntity loaded = null;
	private OrderDetailsEntity order = null;

	@Inject
	ExternalContext extcontext;

	@Inject
	Flash flash;

	@EJB
	OrderProductDAO orderProductDAO;

	@EJB
	UserDAO userDAO;

	@Inject
	FacesContext context;

	@PostConstruct
	public void onLoad() {
		loaded = (OrderDetailsEntity) flash.get("order");

		if (loaded != null) {
			order = loaded;
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błędne użycie systemu", null));
		}
	}

	public List<OrderProductEntity> getFullList() {
		return orderProductDAO.getFullList();
	}

	public List<OrderProductEntity> getList() {
		List<OrderProductEntity> list = null;

		Map<String, Object> searchParams = new HashMap<String, Object>();

		if (order.getIdOrder() > 0) {
			searchParams.put("idOrder", order.getIdOrder());
		}

		list = orderProductDAO.getList(searchParams);

		return list;
	}

	public String editOrderProduct(OrderProductEntity orderProduct) {

		flash.put("orderProduct", orderProduct);

		return "orderEdit?faces-redirect=true";
	}

	public String deleteOrderProduct(OrderProductEntity orderProduct) {
		orderProductDAO.remove(orderProduct);
		return PAGE_STAY_AT_THE_SAME;
	}

	public OrderDetailsEntity getOrder() {
		return order;
	}

	public String close() {
		return PAGE_ORDER_LIST;
	}
}
