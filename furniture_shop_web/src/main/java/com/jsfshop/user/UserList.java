package com.jsfshop.user;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jsf.dao.UserDAO;
import com.jsf.entities.UserEntity;

import jakarta.ejb.EJB;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.Flash;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ViewScoped
public class UserList implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String PAGE_USER_EDIT = "/pages/admin/userEdit?faces-redirect=true";
	private static final String PAGE_REGISTRATION = "/public/registrationPage?faces-redirect=true"; 
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private String surname;

	@Inject
	ExternalContext extcontext;

	@Inject
	Flash flash;

	@EJB
	UserDAO userDAO;

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public List<UserEntity> getFullList() {
		return userDAO.getFullList();
	}

	public List<UserEntity> getList() {
		List<UserEntity> list = null;

		Map<String, Object> searchParams = new HashMap<String, Object>();

		if (surname != null && surname.length() > 0) {
			searchParams.put("surname", surname);
		}

		list = userDAO.getList(searchParams);

		return list;
	}

	public String newUser() {
		UserEntity user = new UserEntity();

		flash.put("user", user);

		return PAGE_REGISTRATION;
	}

	public String editUser(UserEntity user) {
		flash.put("user", user);

		return PAGE_USER_EDIT;
	}

	public String deleteUser(UserEntity user) {
		userDAO.remove(user);
		return PAGE_STAY_AT_THE_SAME;
	}
}
