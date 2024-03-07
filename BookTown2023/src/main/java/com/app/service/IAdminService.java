package com.app.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.app.pojos.Category;
import com.app.pojos.Supplier;
import com.app.pojos.OrderDetails;
import com.app.pojos.Book;
import com.app.pojos.User;

public interface IAdminService {

	public boolean AddSupplier(Supplier supplier);
	public boolean AddProduct(int supplierid, Book product);
	public boolean RemoveSupplier(int supplierid);
	public boolean RemoveProduct(int productid);
	public boolean UpdateProduct(Book product);
	public boolean UpdateSupplier(Supplier supplier);
	public Supplier GetSupplierDetails(int supplierid);
	public Book GetProductDetails(int productid);
	public Category GetCategory(int catid);
	public boolean SetCategory(String category);
	public boolean RemoveCategory(int catid);
	String saveImage(int productId, MultipartFile imgFile) throws IOException;
	byte[] restoreImage(int empId) throws IOException;
	public List<Category> getAllCategory();
	public List<OrderDetails> getAllOrders();
	public List<User> getAllUser();
	boolean UpdateUser(User user);
	byte[] restoreImageAgain(String productName)throws IOException;
}
