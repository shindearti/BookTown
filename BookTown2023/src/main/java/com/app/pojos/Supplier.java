package com.app.pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@Entity
@Table(name = "supplier")
public class Supplier implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "supplier_id")
	private Integer supplierId;

	@Column(length = 20)
	private String firstname;

	@Column(length = 20)
	private String lastname;

	@Column(length = 50)
	private String email;

	@Column(name = "phone_no", length = 15, unique = true )
	private String phoneNo;

	@Column(length = 200)
	private String address;

	@OneToMany(mappedBy = "supplier", cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JsonIgnore
	private List<OrderDetails> orderDetails;

	@OneToMany(mappedBy = "supplier", cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JsonInclude(Include.NON_NULL)
//	@JsonIgnore
	private List<Book> book = new ArrayList<Book>();

	public Supplier() {
		System.out.println("supplier Constructor invoked");
	}
	
	public Supplier(Integer supplierId, String firstname, String lastname, String email, String phone, String address) {
		super();
		this.supplierId = supplierId;			// *****
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.phoneNo = phone;
		this.address = address;
	}
	
	public Supplier(Integer supplierId, String firstname, String lastname) {
		super();
		this.supplierId = supplierId;
		this.firstname = firstname;
		this.lastname = lastname;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer asupplierId) {
		supplierId = asupplierId;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String aFirstname) {
		firstname = aFirstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String aLastname) {
		lastname = aLastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String aEmail) {
		email = aEmail;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String aPhoneNo) {
		phoneNo = aPhoneNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String aAddress) {
		address = aAddress;
	}

	public List<OrderDetails> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetails> aOrderDetails) {
		orderDetails = aOrderDetails;
	}

	public List<Book> getBook() {
		return book;
	}

	public void setBook(List<Book> book) {
		this.book = book;
	}

	@Override
	public String toString() {
		return "Supplier [supplierId=" + supplierId + ", firstname=" + firstname + ", lastname=" + lastname + ", email="
				+ email + ", phoneNo=" + phoneNo + ", address=" + address + ", orderDetails=" + orderDetails
				+ ", book=" + book + "]";
	}

}
