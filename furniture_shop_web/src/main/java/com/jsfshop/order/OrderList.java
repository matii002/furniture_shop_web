package com.jsfshop.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jsf.dao.OrderDAO;
import com.jsf.entities.OrderEntity;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.Flash;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@RequestScoped
public class OrderList {
	private static final String PAGE_ORDER_EDIT = "orderEdit?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private String id;
		
	@Inject
	ExternalContext extcontext;
	
	@Inject
	Flash flash;
	
	@EJB
	OrderDAO orderDAO;
		
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<OrderEntity> getFullList(){
		return orderDAO.getFullList();
	}

	public List<OrderEntity> getList(){
		List<OrderEntity> list = null;
		
		Map<String,Object> searchParams = new HashMap<String, Object>();
		
		if (id != null && id.length() > 0){
			searchParams.put("id", id);
		}
		
		list = orderDAO.getList(searchParams);
		
		return list;
	}

	public String newOrder(){
		OrderEntity order = new OrderEntity();
		
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("order", order);
		
		//2. Pass object through flash	
		flash.put("order", order);
		
		return PAGE_ORDER_EDIT;
	}

	public String editOrder(OrderEntity order){
		//1. Pass object through session
		//HttpSession session = (HttpSession) extcontext.getSession(true);
		//session.setAttribute("Product", Product);
		
		flash.put("order", order);
		
		return PAGE_ORDER_EDIT;
	}

	public String deleteOrder(OrderEntity order){
		orderDAO.remove(order);
		return PAGE_STAY_AT_THE_SAME;
	}
}
