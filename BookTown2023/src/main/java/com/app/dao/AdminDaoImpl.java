package com.app.dao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.pojos.Category;
import com.app.pojos.Supplier;
import com.app.pojos.OrderDetails;
import com.app.pojos.Book;
import com.app.pojos.User;


@Repository
public class AdminDaoImpl implements IAdminDao {

	@PersistenceContext
	private EntityManager mgr;
	
	@Autowired
	private ISupplierDao fdao;
	
	@Override
	public boolean AddSupplier(Supplier supplier) {
		for(Book book : supplier.getBook()) {
			book.setSupplier(supplier);
			mgr.persist(book);
		}
		mgr.persist(supplier);
		return true;
	}

	@Override
	public boolean AddProduct(int supplierid, Book book) {
		Supplier supplier = fdao.getSupplierDetails(supplierid);
		book.setSupplier(supplier);
		mgr.persist(book);
		return true;
	}
	
	@Override
	public boolean RemoveSupplier(int supplierId) {
		boolean success = false;
		Supplier f = mgr.find(Supplier.class, supplierId);
		List<Book> s = f.getBook();
		for(Book sd : s) {
			mgr.remove(sd);
		}
		
		mgr.remove(f);
		success = true;
		return success;
	}

	@Override
	public boolean RemoveProduct(int productid) {
		boolean success = false;
		Book product = mgr.find(Book.class, productid);
		mgr.remove(product);
		success = true;
		return success;
	}

	@Override
	public boolean UpdateSupplier(Supplier supplier) {
		mgr.unwrap(Session.class).update(supplier);
		return true;
	}

	@Override
	public boolean UpdateProduct(Book product) {
		mgr.unwrap(Session.class).update(product);
		return true;
	}

	@Override
	public Book GetProductDetails(int productid) {
		return mgr.find(Book.class, productid);
	}

	@Override
	public Supplier GetSupplierDetails(int supplierid) {
		return mgr.find(Supplier.class, supplierid);
	}

	@Override
	public Category GetCategory(int catid) {
		return mgr.find(Category.class, catid);
	}
	
	@Override
	public boolean SetCategory(String category) {
		Category c = new Category();
		c.setCategoryName(category);
		mgr.persist(c);
		return true;
	}
	
	@Override
	public boolean RemoveCategory(int catid) {
		boolean success = false;
		Category c = mgr.find(Category.class, catid);
		mgr.remove(c);
		success = true;
		return success;
	}

	@Override
	public String saveImage(int productId, MultipartFile imgFile) throws IOException {    
		Book s = mgr.find(Book.class, productId);
		String path = imgFile.getOriginalFilename();
		System.out.println("path {}"+ path);
		s.setImagePath(path);
		Files.copy(imgFile.getInputStream(), Paths.get(path), StandardCopyOption.REPLACE_EXISTING);
		
		return "file copied";
	}

	@Override
	public byte[] restoreImage(int empId) throws IOException {
		Book s = mgr.find(Book.class, empId);
		String path = s.getImagePath();
		if (path != null)
			return Files.readAllBytes(Paths.get(path));
		throw new ResourceNotFoundException("Image not  yet assigned , for " + s.getTitle());
			
	}
	
	@Override
	public List<Category> getAllCategory() {
		String jpql = "SELECT NEW com.app.pojos.Category(c.categoryId, c.categoryName) FROM Category c";
		return mgr.createQuery(jpql, Category.class).getResultList();
	}
	
	@Override
	public List<OrderDetails> getAllOrders() {
		String jpql = "SELECT NEW com.app.pojos.OrderDetails(o.id, o.orderItem, o.quantity, o.amount, o.orders) FROM OrderDetails o";
		return mgr.createQuery(jpql, OrderDetails.class).getResultList();
	}

	@Override
	public List<User> getAllUser() {
		String jpql = "SELECT NEW com.app.pojos.User(u.userId, u.email, u.password, u.phoneNo, u.address,"
				+ "u.firstname, u.lastname, u.isadmin) FROM User u";
		return mgr.createQuery(jpql, User.class).getResultList();
	}
	
	@Override
	public boolean UpdateUser(User user) {
		mgr.unwrap(Session.class).update(user);
		return true;
	}

	
}
