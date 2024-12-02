package com.jsfshop.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jsf.dao.ProductDAO;
import com.jsf.entities.ProductEntity;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.Flash;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@RequestScoped
public class ProductList {
	private static final String PAGE_PRODUCT_EDIT = "productEdit?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private String name;
		
	@Inject
	ExternalContext extcontext;
	
	@Inject
	Flash flash;
	
	@EJB
	ProductDAO productDAO;
		
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ProductEntity> getFullList(){
		return productDAO.getFullList();
	}

	public List<ProductEntity> getList(){
		List<ProductEntity> list = null;
		
		//1. Prepare search params
		Map<String,Object> searchParams = new HashMap<String, Object>();
		
		if (name != null && name.length() > 0){
			searchParams.put("name", name);
		}
		
		//2. Get list
		list = productDAO.getList(searchParams);
		
		return list;
	}

	public String newProduct(){
		ProductEntity product = new ProductEntity();
		
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("Product", Product);
		
		//2. Pass object through flash	
		flash.put("product", product);
		
		return PAGE_PRODUCT_EDIT;
	}

	public String editProduct(ProductEntity product){
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("Product", Product);
		
		//2. Pass object through flash 
		flash.put("product", product);
		
		return PAGE_PRODUCT_EDIT;
	}

	public String deleteProduct(ProductEntity product){
		productDAO.remove(product);
		return PAGE_STAY_AT_THE_SAME;
	}
}
