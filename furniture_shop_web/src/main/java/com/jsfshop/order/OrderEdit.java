package com.jsfshop.order;

import java.io.IOException;
import java.io.Serializable;

import com.jsf.dao.OrderDAO;
import com.jsf.entities.OrderEntity;

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

	private static final String PAGE_ORDER_LIST = "orderList?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private OrderEntity order = new OrderEntity();
	private OrderEntity loaded = null;

	@EJB
	OrderDAO orderDAO;

	@Inject
	FacesContext context;

	@Inject
	Flash flash;
	
	public OrderEntity getOrder() {
		return order;
	}

	public void onLoad() throws IOException {
		// 1. load Product passed through session
		// HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
		// loaded = (Product) session.getAttribute("Product");

		// 2. load Product passed through flash
		loaded = (OrderEntity) flash.get("order");

		// cleaning: attribute received => delete it from session
		if (loaded != null) {
			order = loaded;
			// session.removeAttribute("Product");
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błędne użycie systemu", null));
			// if (!context.isPostback()) { //possible redirect
			// context.getExternalContext().redirect("ProductList.xhtml");
			// context.responseComplete();
			// }
		}

	}

	public String saveData() {
		// no Product object passed
		if (loaded == null) {
			return PAGE_STAY_AT_THE_SAME;
		}

		try {
			if (order.getIdOrder() == 0) {
				// new record
				orderDAO.create(order);
			} else {
				// existing record
				orderDAO.merge(order);
			}
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_ORDER_LIST;
	}
}