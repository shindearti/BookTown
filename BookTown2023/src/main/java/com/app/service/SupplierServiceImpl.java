package com.app.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.ISupplierDao;
import com.app.pojos.Supplier;
import com.app.pojos.Book;


@Service
@Transactional
public class SupplierServiceImpl implements ISupplierService {

	@Autowired
	private ISupplierDao f_dao;
	
	@Override
	public List<Supplier> getSupplierList() {
		return f_dao.getAllSupplier();
	}

	@Override
	public List<Book> getSupplierStock(int supplierid) {
		return f_dao.getSupplierStock(supplierid);
	}

	@Override
	public Book getProductDetails(int supplierid, int productid) {
		return f_dao.getProductDetails(supplierid, productid);
	}

	@Override
	public Supplier getSupplierDetails(int id) {
		return f_dao.getSupplierDetails(id);
	}

	@Override
	public List<Book> getAllProduct() {
		return f_dao.getAllProduct();
	}
	
	
}
