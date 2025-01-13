package com.jsfshop.buy;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.jsf.dao.OrderDetailsDAO;
import com.jsf.dao.OrderProductDAO;
import com.jsf.entities.OrderDetailsEntity;
import com.jsf.entities.OrderProductEntity;
import com.jsf.entities.UserEntity;
import com.jsfshop.cart.Cart;
import com.jsfshop.cart.CartItem;

import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.simplesecurity.RemoteClient;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;

@Named
@ViewScoped
public class BuyProduct implements Serializable {

	private static final long serialVersionUID = 1L;
	private OrderDetailsEntity order = new OrderDetailsEntity();

	@EJB
	OrderDetailsDAO orderDetailsDAO;

	@EJB
	OrderProductDAO orderProductDAO;

	@Inject
	FacesContext context;

	@Inject
	private Cart cart;

	public void buy() {
		if (cart.getItems() != null && !cart.getItems().isEmpty()) {
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext()
					.getSession(false);

			RemoteClient<UserEntity> remoteClient = (RemoteClient<UserEntity>) session.getAttribute("remoteClient");

			if (remoteClient != null) {

				UserEntity user = remoteClient.getDetails();

				order.setUser(user);
				order.setOrderDate(LocalDateTime.now());
				order.setFullPrice(cart.getTotal());
				orderDetailsDAO.create(order);

				for (CartItem item : cart.getItems()) {
					OrderProductEntity orderProduct = new OrderProductEntity();
					orderProduct.setOrderDetail(order);
					orderProduct.setQuantity(item.getQuantity());
					orderProduct.setProduct(item.getProduct());
					orderProductDAO.create(orderProduct);
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Złożono zamówienie.", null));
				}
				cart.removeAll();
			} else {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Wystąpił błąd podczas skałdania zamówienia. Przepraszamy za utrudnienia, proszę spróbować pózniej.",
						null));
			}
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Koszyk jest pusty.", null));
		}
	}
}
