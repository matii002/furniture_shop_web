package com.jsfshop.product;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.FilterMeta;

import com.jsf.dao.ProductDAO;
import com.jsf.entities.ProductEntity;

import jakarta.ejb.EJB;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.Flash;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ViewScoped
public class ProductList implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String PAGE_PRODUCT_EDIT = "productEdit?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private String name;

	@Inject
	ExternalContext extcontext;

	@Inject
	Flash flash;

	@EJB
	ProductDAO productDAO;

	private LazyDataModel<ProductEntity> model;

	public List<ProductEntity> init() {
		return productDAO.getFullList();
	}

	public ProductList() {
		model = new LazyDataModel<ProductEntity>() {
			private static final long serialVersionUID = 1L;

			@Override
			public int count(Map<String, FilterMeta> filterBy) {
				return 0;
			}

			@Override
			public List<ProductEntity> load(int first, int pageSize, Map<String, SortMeta> sortBy,
					Map<String, FilterMeta> filterBy) {
				List<ProductEntity> list = null;

				Map<String, Object> searchParams = new HashMap<String, Object>();

				if (name != null && name.length() > 0) {
					searchParams.put("name", name);
				}

				list = productDAO.getList(searchParams, first, pageSize);

				int totalCount = productDAO.count(searchParams);

				setRowCount(totalCount);

				return list;
			}
		};
	}

	public LazyDataModel<ProductEntity> getModel() {
		return model;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ProductEntity> getFullList() {
		return productDAO.getFullList();
	}

	public List<ProductEntity> getList() {
		List<ProductEntity> list = null;

		Map<String, Object> searchParams = new HashMap<String, Object>();

		if (name != null && name.length() > 0) {
			searchParams.put("name", name);
		}

		list = productDAO.getList(searchParams);

		return list;
	}

	public String newProduct() {
		ProductEntity product = new ProductEntity();

		flash.put("product", product);

		return PAGE_PRODUCT_EDIT;
	}

	public String editProduct(ProductEntity product) {
		flash.put("product", product);

		return PAGE_PRODUCT_EDIT;
	}

	public String deleteProduct(ProductEntity product) {
		productDAO.remove(product);
		return PAGE_STAY_AT_THE_SAME;
	}
}