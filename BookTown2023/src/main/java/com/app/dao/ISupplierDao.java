package com.app.dao;

import java.util.List;

import com.app.pojos.Supplier;
import com.app.pojos.Book;

public interface ISupplierDao {
	
	List<Book> getAllProduct();
	List<Supplier> getAllSupplier();
	Supplier getSupplierDetails(int id);
	List<Book> getSupplierStock(int supplierid);
	Book getProductDetails(int farmerid, int productid);

}
