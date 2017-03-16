package com.sombra.project.parse;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "commodities")
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

	public Commodity() {

	}

	public Commodity(String name, String brand, String country, String consist, int price, int available,
			SubCategory subCategory) {
		this.name = name;
		this.brand = brand;
		this.country = country;
		this.consist = consist;
		this.price = price;
		this.available = available;
		this.subCategory = subCategory;
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

	@Override
	public String toString() {
		return "Commodity [id=" + id + ", name=" + name + ", brand=" + brand + ", country=" + country + ", consist="
				+ consist + ", price=" + price + ", available=" + available + ", image=" + image + ", subCategory="
				+ subCategory + "]";
	}

}
