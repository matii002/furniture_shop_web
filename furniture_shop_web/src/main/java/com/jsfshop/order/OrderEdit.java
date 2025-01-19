package com.jsfshop.order;

import java.io.IOException;
import java.io.Serializable;

import com.jsf.dao.OrderDetailsDAO;
import com.jsf.entities.OrderDetailsEntity;

import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ViewScoped
public class OrderEdit implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_STAY_AT_THE_SAME = null;
	private static final String PAGE_ORDER_LIST = "/pages/shop-assistant/orderList?faces-redirect=true";

	private int idOrder;
	private OrderDetailsEntity order = new OrderDetailsEntity();
	private OrderDetailsEntity loaded = null;

	@EJB
	OrderDetailsDAO orderDetailsDAO;

	@Inject
	FacesContext context;

	@Inject
	Flash flash;

	public OrderDetailsEntity getOrder() {
		return order;
	}

	public void onLoad() throws IOException {
		loaded = (OrderDetailsEntity) flash.get("order");

		if (loaded != null) {
			this.idOrder = loaded.getIdOrder();
			order = orderDetailsDAO.find(idOrder);
			if (order == null) {
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nie znaleziono zamówienia o podanym id.", null));
			}
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błędne użycie systemu", null));
		}

	}

	public String saveData() {
		if (loaded == null) {
			return PAGE_STAY_AT_THE_SAME;
		}

		try {
			if (order.getIdOrder() == 0) {
				orderDetailsDAO.create(order);
			} else {
				orderDetailsDAO.merge(order);
			}
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_ORDER_LIST;

	}

	public String close() {
		return PAGE_ORDER_LIST;
	}
}