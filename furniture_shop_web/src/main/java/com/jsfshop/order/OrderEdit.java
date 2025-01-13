package com.jsfshop.order;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.jsf.dao.OrderDetailsDAO;
import com.jsf.dao.OrderProductDAO;
import com.jsf.entities.OrderDetailsEntity;
import com.jsf.entities.OrderProductEntity;
import com.jsf.entities.UserEntity;
import com.jsfshop.cart.Cart;
import com.jsfshop.cart.CartItem;

import jakarta.faces.simplesecurity.RemoteClient;

import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;

@Named
@ViewScoped
public class OrderEdit implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_STAY_AT_THE_SAME = null;
	private static final String PAGE_ORDER_PRODUCT_LIST = "/pages/shop-assistant/orderProductList?faces-redirect=true";

	private int idOrder;
	private OrderDetailsEntity order = new OrderDetailsEntity();
	private OrderProductEntity loaded = null;
	/* private UserEntity user; */

	@EJB
	OrderDetailsDAO orderDetailsDAO;

	@Inject
	FacesContext context;

	@Inject
	Flash flash;

	public OrderDetailsEntity getOrder() {
		return order;
	}

	/*
	 * public void setUser(UserEntity user) { this.user = user; }
	 * 
	 * public UserEntity getUser() { return user; }
	 */
	public void onLoad() throws IOException {
		// 1. load Product passed through session
		// HttpSession session = (HttpSession)
		// context.getExternalContext().getSession(true);
		// loaded = (Product) session.getAttribute("Product");

		// 2. load Product passed through flash
		loaded = (OrderProductEntity) flash.get("orderProduct");

		// cleaning: attribute received => delete it from session
		if (loaded != null) {
			this.idOrder = loaded.getOrderDetail().getIdOrder();
			// session.removeAttribute("Product");
			order = orderDetailsDAO.find(idOrder);
			if (order == null) {
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nie znaleziono zamówienia o podanym id.", null));
			}
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
				orderDetailsDAO.create(order);
			} else {
				// existing record
				orderDetailsDAO.merge(order);
			}
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_ORDER_PRODUCT_LIST;

	}

	public String close() {
		return PAGE_ORDER_PRODUCT_LIST;
	}
}