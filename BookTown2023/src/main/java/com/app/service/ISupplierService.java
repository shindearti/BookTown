package com.app.service;

import java.util.List;

import com.app.pojos.Supplier;
import com.app.pojos.Book;

public interface ISupplierService {
	
	List<Supplier> getSupplierList();
	Supplier getSupplierDetails(int id);
	List<Book> getSupplierStock(int supplierid);
	Book getProductDetails(int supplierid, int productid);
	List<Book> getAllProduct();
	
}
