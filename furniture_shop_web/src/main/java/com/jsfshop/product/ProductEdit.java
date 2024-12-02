package com.jsfshop.product;

import java.io.IOException;
import java.io.Serializable;

import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;

import com.jsf.dao.ProductDAO;
import com.jsf.entities.ProductEntity;

@Named
@ViewScoped
public class ProductEdit implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_PRODUCT_LIST = "productList?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private ProductEntity product = new ProductEntity();
	private ProductEntity loaded = null;

	@EJB
	ProductDAO productDAO;

	@Inject
	FacesContext context;

	@Inject
	Flash flash;
	
	//do czego?? 
	public ProductEntity getProduct() {
		return product;
	}

	public void onLoad() throws IOException {
		// 1. load Product passed through session
		// HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
		// loaded = (Product) session.getAttribute("Product");

		// 2. load Product passed through flash
		loaded = (ProductEntity) flash.get("product");

		// cleaning: attribute received => delete it from session
		if (loaded != null) {
			product = loaded;
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
			if (product.getIdProduct() == 0) {
				// new record
				productDAO.create(product);
			} else {
				// existing record
				productDAO.merge(product);
			}
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_PRODUCT_LIST;
	}
}
