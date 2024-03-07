package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.pojos.Category;
import com.app.pojos.Supplier;
import com.app.pojos.Book;
import com.app.service.ISupplierService;

@CrossOrigin("*")
@RestController
@RequestMapping("/supplier")
public class SupplierController {

	@Autowired
	private ISupplierService f_service;

	@GetMapping("/list")
	public ResponseEntity<?> supplierList() {
		System.out.println("in getSupplierList");
		List<Supplier> list = f_service.getSupplierList();
		return new ResponseEntity<List<Supplier>>(list, HttpStatus.OK);
	}

	@GetMapping("/supplierdetails/{supplierid}")
	public ResponseEntity<?> getSupplierDetails(@PathVariable int supplierid) {
		return new ResponseEntity<Supplier>(f_service.getSupplierDetails(supplierid), HttpStatus.OK);
	}

	@GetMapping("/products/{supplierid}")
	public ResponseEntity<?> bookDetails(@PathVariable int supplierid) {
		List<Book> products = f_service.getSupplierStock(supplierid);
		return new ResponseEntity<List<Book>>(products, HttpStatus.OK);
	}

	@GetMapping("/products/{supplierid}/{productid}")
	public ResponseEntity<?> productDetails(@PathVariable int supplierid, @PathVariable int productid) {
		Book product = f_service.getProductDetails(supplierid, productid);
		return new ResponseEntity<Book>(product, HttpStatus.OK);
	}
	
	@GetMapping("/allproducts")
	public ResponseEntity<?> productlist() {
		System.out.println("in productlist");
		List<Book> list = f_service.getAllProduct();
		return new ResponseEntity<List<Book>>(list, HttpStatus.OK);
	}

}
