package com.jsfshop.orderProduct;

import java.io.IOException;
import java.io.Serializable;

import com.jsf.dao.OrderProductDAO;
import com.jsf.entities.OrderProductEntity;

import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ViewScoped
public class OrderProductEdit implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_ORDER_PRODUCT_LIST = "/pages/shop-assistant/orderProductList?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private OrderProductEntity orderProduct = new OrderProductEntity();
	private OrderProductEntity loaded = null;

	@EJB
	OrderProductDAO orderProductDAO;
	@Inject
	FacesContext context;

	@Inject
	Flash flash;

	public OrderProductEntity getOrderProduct() {
		return orderProduct;
	}

	public void onLoad() throws IOException {

		loaded = (OrderProductEntity) flash.get("orderProduct");

		if (loaded != null) {
			orderProduct = loaded;
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błędne użycie systemu", null));
		}

	}

	public String saveData() {
		if (loaded == null) {
			return PAGE_STAY_AT_THE_SAME;
		}

		try {
			if (orderProduct.getIdOrderProduct() == 0) {
				orderProductDAO.create(orderProduct);
			} else {
				orderProductDAO.merge(orderProduct);
				System.out.println("Order Finish: " + orderProduct.getOrderDetail().getOrderFinish());
			}
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_ORDER_PRODUCT_LIST;
	}
}