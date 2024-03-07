package com.app.pojos;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@Entity
@Table(name = "book")
@JsonInclude(Include.NON_NULL)
public class Book implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "book_id", unique = true, nullable = false, length = 10)
	private Integer id;

	@Column(name = "title", nullable = false, length = 50, unique = true)
	private String title;

	
	@Column(name = "author", nullable = false, length = 50, unique = true)
	private String author;
	
	@Column(nullable = false, length = 10)
	private int stock;

	@Column(name = "unit_price", precision = 12)
	private float unitPrice;

	@ManyToOne
	@JoinColumn(name = "category_id")
//	@JsonIgnore
	private Category category;

	@ManyToOne
	@JoinColumn(name = "supplier_id")
//	@JsonIgnore
	private Supplier supplier;
	
	@Column(name = "book_image", length = 400)
	private String imagePath;

	public Book() {
		System.out.println("Book Constructor invoked");
	}
	
	public Book(Integer id, String title,String author, float unitPrice) {
		super();
		this.id = id;
		this.title = title;
		this.author=author;
		this.unitPrice = unitPrice;
	}
	
	public Book(Integer id, String title, String author,int stock, float unitPrice, Category category) {
		super();
		this.id = id;
		this.title = title;
		this.author=author;
		this.stock = stock;
		this.unitPrice = unitPrice;
		this.category = category;
	}

	public Book(Integer id, String title,String author, int stock, float unitPrice, Category category, String imagePath) {
		super();
		this.id = id;
		this.title = title;
		this.author=author;
		this.stock = stock;
		this.unitPrice = unitPrice;
		this.category = category;
		this.imagePath = imagePath;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String atitle) {
		title = atitle;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int astock) {
		stock = astock;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public float getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(float aunitPrice) {
		unitPrice = aunitPrice;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category aCategory) {
		category = aCategory;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", stock=" + stock + ", unitPrice="
				+ unitPrice + ", category=" + category + ", Supplier=" + supplier + ", imagePath=" + imagePath + "]";
	}
	
	
	

}
