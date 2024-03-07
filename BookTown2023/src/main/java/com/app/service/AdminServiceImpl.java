package com.app.service;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.dao.IAdminDao;
import com.app.pojos.Category;
import com.app.pojos.Supplier;
import com.app.pojos.OrderDetails;
import com.app.pojos.Book;
import com.app.pojos.User;
import com.app.repository.BookRepository;


@Service
@Transactional
public class AdminServiceImpl implements IAdminService {
	
	@Autowired
	private IAdminDao a_dao;
	
	@Autowired
	private BookRepository stockRepository;

	@Override
	public boolean AddSupplier(Supplier supplier) {
		return a_dao.AddSupplier(supplier);
	}

	@Override
	public boolean AddProduct(int farmerid, Book product) {
		return a_dao.AddProduct(farmerid, product);
	}
	
	@Override
	public boolean RemoveSupplier(int supplierid) {
		return a_dao.RemoveSupplier(supplierid);
	}

	@Override
	public boolean RemoveProduct(int productid) {
		return a_dao.RemoveProduct(productid);
	}

	@Override
	public boolean UpdateProduct(Book product) {
		return a_dao.UpdateProduct(product);
	}

	@Override
	public boolean UpdateSupplier(Supplier supplier) {
		return a_dao.UpdateSupplier(supplier);
	}

	@Override
	public Supplier GetSupplierDetails(int supplierid) {
		return a_dao.GetSupplierDetails(supplierid);
	}

	@Override
	public Book GetProductDetails(int productid) {
		return a_dao.GetProductDetails(productid);
	}

	@Override
	public Category GetCategory(int catid) {
		return a_dao.GetCategory(catid);
	}

	@Override
	public boolean SetCategory(String category) {
		return a_dao.SetCategory(category);
	}

	@Override
	public boolean RemoveCategory(int catid) {
		return a_dao.RemoveCategory(catid);
	}

	@Override
	public String saveImage(int productId, MultipartFile imgFile) throws IOException {
		return a_dao.saveImage(productId, imgFile);
	}

	@Override
	public byte[] restoreImage(int empId) throws IOException {
		return a_dao.restoreImage(empId);
	}
	
	@Override
	public List<Category> getAllCategory(){
		return a_dao.getAllCategory();
	}
	
	@Override
	public List<OrderDetails> getAllOrders() {
		return a_dao.getAllOrders();
	}

	@Override
	public List<User> getAllUser() {
		// TODO Auto-generated method stub
		return a_dao.getAllUser();
	}

	@Override
	public boolean UpdateUser(User user) {
		return a_dao.UpdateUser(user);
	}

	public byte[] restoreImageAgain(String productName) throws IOException {
		Book s = stockRepository.findByTitle(productName);
		String path = s.getImagePath();
		if (path != null)
			return Files.readAllBytes(Paths.get(path));
		throw new ResourceNotFoundException("Image not  yet assigned , for " + s.getTitle());
	}

}
