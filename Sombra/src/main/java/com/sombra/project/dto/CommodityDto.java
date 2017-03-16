package com.sombra.project.dto;

import java.util.List;

public class CommodityDto implements Comparable<CommodityDto> {

	private int id;
	private String name;
	private String brand;
	private String country;
	private String consist;
	private int price;
	private int available;
	private String image;

	private SubCategoryDto subCategory;

	private List<OrderDto> orders;

	public CommodityDto() {

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

	public SubCategoryDto getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(SubCategoryDto subCategory) {
		this.subCategory = subCategory;
	}

	public List<OrderDto> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderDto> orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return "CommodityDto [id=" + id + ", name=" + name + ", brand=" + brand + ", country=" + country + ", consist="
				+ consist + ", price=" + price + ", available=" + available + ", subCategory=" + subCategory + "]";
	}

	public int compareTo(CommodityDto commodityDto) {
		int result = this.name.compareTo(commodityDto.getName());
		if (result == 0) {
			result = this.id - commodityDto.getId();
		}
		return result;
	}

}
