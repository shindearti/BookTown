package com.app.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.pojos.Category;
import com.app.pojos.Supplier;
import com.app.pojos.OrderDetails;
import com.app.pojos.Book;
import com.app.pojos.User;
import com.app.service.IAdminService;
import com.app.service.ISupplierService;
import com.app.service.IUserService;


@CrossOrigin("*")
@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private IAdminService service;
	
	@Autowired
	private ISupplierService f_service;
	
	@Autowired
	private IUserService u_service;

	@PostMapping("/newSupplier")
	public ResponseEntity<?> AddNewSupplier(@RequestBody Supplier supplier) {
		System.out.println(supplier.getBook());
		service.AddSupplier(supplier);
		return new ResponseEntity<Supplier>(supplier, HttpStatus.OK);
	}
	
	@PostMapping("/newproduct/{supplierid}")
	public ResponseEntity<?> AddNewProduct(@PathVariable int supplierid, @RequestBody Book product) {
		service.AddProduct(supplierid, product);
		return new ResponseEntity<String>("Product Added Successfully", HttpStatus.OK);
	}
	
	@PostMapping("/{productid}/image")
	public ResponseEntity<?> uploadImage(@PathVariable int productid, @RequestParam MultipartFile imgFile)
			throws IOException {
		System.out.println(imgFile);
		System.out.println("product id " + productid);
		System.out.println("uploaded file name :  " + imgFile.getOriginalFilename() + " size " + imgFile.getSize());
		String msg = service.saveImage(productid, imgFile);
		return new ResponseEntity<String>(msg, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{productid}", produces = { MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE,
			MediaType.IMAGE_PNG_VALUE }) 
	public ResponseEntity<?> downloadImage(@PathVariable int productid)  throws IOException{
		System.out.println("in img download " + productid);
		return ResponseEntity.ok(service.restoreImage(productid));
	}
	
	@GetMapping("/removesupplier/{supplierid}")
	public ResponseEntity<?> DeleteSupplier(@PathVariable int supplierid) {
		service.RemoveSupplier(supplierid);
		return new ResponseEntity<String>("Supplier Removed Successfully", HttpStatus.OK);
	}
	
	@GetMapping("removeproduct/{productid}")
	public ResponseEntity<?> DeleteProduct(@PathVariable int productid) {
		service.RemoveProduct(productid);
		return new ResponseEntity<String>("Product Removed Successfully", HttpStatus.OK);
	}

	@PutMapping("updateproduct/{productid}")
	public ResponseEntity<?> UpdateProduct(@PathVariable int productid, @RequestParam("title") String title,
			@RequestParam("unitPrice") float unitprice, @RequestParam("catid") int catid, 
			@RequestParam("stock") int stock) {
		Book product = service.GetProductDetails(productid);
		if (product != null) {
			Category category = service.GetCategory(catid);
			product.setUnitPrice(unitprice);
			product.setTitle(title);
			product.setCategory(category);
			product.setStock(stock);
			service.UpdateProduct(product);
			return new ResponseEntity<String>("Product Updated", HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}

	@PostMapping("updatesupplier/{supplierid}")
	public ResponseEntity<?> UpdateSupplier(@PathVariable int supplierid, @RequestBody Supplier f) {
		System.out.println("RequestBody : " + f);
		Supplier supplier = f_service.getSupplierDetails(supplierid);
		System.out.println("old Supplier : " + supplier);
		if (supplier != null) {
			supplier.setFirstname(f.getFirstname());
			supplier.setLastname(f.getLastname());
			supplier.setEmail(f.getEmail());
			supplier.setAddress(f.getAddress());
			supplier.setPhoneNo(f.getPhoneNo());
			service.UpdateSupplier(supplier);
			return new ResponseEntity<Supplier>(supplier, HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/addcategory/{category}")
	public ResponseEntity<?> AddCategory(@PathVariable String category){
		service.SetCategory(category);
		return new ResponseEntity<String>("New Category: "+category+" Added", HttpStatus.OK);
	}
	
	@GetMapping("/removecategory/{catid}")
	public ResponseEntity<?> removeCategory(@PathVariable int catid){
		service.RemoveCategory(catid);
		return new ResponseEntity<String>("Category Removed", HttpStatus.OK);
	}
	
	@GetMapping("/categorylist")
	public ResponseEntity<?> categorylist() {
		System.out.println("in categorylist");
		List<Category> list = service.getAllCategory();
		return new ResponseEntity<List<Category>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/allorders")
	public ResponseEntity<?> getAllOrders(){
		System.out.println("in getAllOrders");
		List<OrderDetails> list = service.getAllOrders();
		return new ResponseEntity<List<OrderDetails>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/allusers")
	public ResponseEntity<?> getAllUsers(){
		System.out.println("in getAllUsers");
		List<User> list = service.getAllUser();
		return new ResponseEntity<List<User>>(list, HttpStatus.OK);
	}
	
	@PostMapping("/updateuser/{userId}")
	public ResponseEntity<?> UpdateUser(@PathVariable int userId, @RequestBody User user) {
		System.out.println("inside UpdateUser" + userId);
		User u = u_service.getUserDetails(userId);
		if (u != null) {
			u.setFirstname(user.getFirstname());
			u.setLastname(user.getLastname());
			u.setEmail(user.getEmail());
			u.setAddress(user.getAddress());
			u.setPhoneNo(user.getPhoneNo());
			service.UpdateUser(user);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/userdetails/{userId}")
	public ResponseEntity<?> getUserDetails(@PathVariable int userId){
		System.out.println("in getUserDetails");
		User u = u_service.getUserDetails(userId);
		return new ResponseEntity<User>(u, HttpStatus.OK);
	}
	
	@GetMapping(value = "/image/{productName}", produces = { MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE,
			MediaType.IMAGE_PNG_VALUE }) 
	public ResponseEntity<?> downloadImageAgain(@PathVariable String productName)  throws IOException{
		System.out.println("in img download " + productName);
		return ResponseEntity.ok(service.restoreImageAgain(productName));
	}
}
