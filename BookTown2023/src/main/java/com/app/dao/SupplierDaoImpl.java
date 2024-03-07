package com.app.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import com.app.pojos.Supplier;
import com.app.pojos.Book;


@Repository
public class SupplierDaoImpl implements ISupplierDao {

	@PersistenceContext
	private EntityManager mgr;
	
	@Override
	public List<Supplier> getAllSupplier() {
		String jpql = "SELECT NEW com.app.pojos.Supplier(s.supplierId, s.firstname, s.lastname, s.email, s.phoneNo, s.address) FROM Supplier s";
		return mgr.createQuery(jpql, Supplier.class).getResultList();
	}

	@Override
	public List<Book> getSupplierStock(int supplierid) {
		String jpql = "SELECT NEW com.app.pojos.Book(b.id, b.title,b.author, b.unitPrice) FROM Book b JOIN b.supplier s WHERE s.supplierId=:frmr";
		return mgr.createQuery(jpql, Book.class).setParameter("frmr", supplierid).getResultList();
	}

	@Override
	public Book getProductDetails(int supplierid, int productid) {
		String jpql = "SELECT NEW com.app.pojos.Book(b.id, b.title,b.author b.stock, b.unitPrice, b.category) FROM Book b JOIN b.supplier s WHERE s.supplierId=:frmr AND b.id=:prdct";
		return mgr.createQuery(jpql, Book.class).setParameter("frmr", supplierid).setParameter("prdct", productid).getSingleResult();
	}
	

	@Override
	public Supplier getSupplierDetails(int id) {
		String jpql = "SELECT NEW com.app.pojos.Supplier(s.supplierId, s.firstname, s.lastname, s.email, s.phoneNo, s.address) FROM Supplier s WHERE s.supplierId=:sid";
		return mgr.createQuery(jpql, Supplier.class).setParameter("sid", id).getSingleResult();
	}

	@Override
	public List<Book> getAllProduct() {
		String jpql = "SELECT NEW com.app.pojos.Book(b.id, b.title,b.author, b.stock, b.unitPrice, b.category, b.imagePath) FROM Book b ";
		return mgr.createQuery(jpql, Book.class).getResultList();
	}
	
	

}
