package com.jsfshop.user;

import java.io.IOException;
import java.io.Serializable;

import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import com.jsf.dao.UserDAO;
import com.jsf.entities.UserEntity;

@Named
@ViewScoped
public class UserEdit implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String PAGE_USER_LIST = "userList?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private UserEntity user = new UserEntity();
	private UserEntity loaded = null;

	@EJB
	UserDAO userDAO;

	@Inject
	FacesContext context;

	@Inject
	Flash flash;

	public UserEntity getUser() {
		return user;
	}

	public void onLoad() throws IOException {
		loaded = (UserEntity) flash.get("user");

		if (loaded != null) {
			user = loaded;
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błędne użycie systemu", null));
		}

	}

	public String saveData() {
		if (loaded == null) {
			return PAGE_STAY_AT_THE_SAME;
		}

		try {
			if (user.getIdUser() == 0) {
				userDAO.create(user);
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Utworzono nowego uzytkownika.", null));
			} else {
				userDAO.merge(user);
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Zaktualizowano pomyślnie.", null));
			}
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_USER_LIST;
	}
}
