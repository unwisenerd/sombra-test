package com.sombra.project.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "commodities")
@NamedQueries({ @NamedQuery(name = "Commodity.findAll", query = "SELECT c FROM Commodity c"),
		@NamedQuery(name = "Commodity.findAllWithSubCategory", query = "SELECT DISTINCT c FROM Commodity c LEFT JOIN FETCH c.subCategory"),
		@NamedQuery(name = "Commodity.findBrands", query = "SELECT DISTINCT c.brand FROM Commodity c"),
		@NamedQuery(name = "Commodity.findCountries", query = "SELECT DISTINCT c.country FROM Commodity c"),
		@NamedQuery(name = "Commodity.findMinimumPrice", query = "SELECT MIN(c.price) FROM Commodity c"),
		@NamedQuery(name = "Commodity.findMaximumPrice", query = "SELECT MAX(c.price) FROM Commodity c") })
public class Commodity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String brand;
	private String country;
	private String consist;
	private int price;
	private int available;
	private String image;

	@ManyToOne
	@JoinColumn(name = "subcategory")
	private SubCategory subCategory;

	@OneToMany(mappedBy = "commodity", cascade = CascadeType.REMOVE)
	private List<Order> orders;

	public Commodity() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getConsist() {
		return consist;
	}

	public void setConsist(String consist) {
		this.consist = consist;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getAvailable() {
		return available;
	}

	public void setAvailable(int available) {
		this.available = available;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public SubCategory getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

}
