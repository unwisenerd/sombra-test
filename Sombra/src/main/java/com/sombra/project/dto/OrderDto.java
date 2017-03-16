package com.sombra.project.dto;

public class OrderDto {

	private int id;
	private int quantity;
	private boolean paid;
	private String card;
	private String document;

	private CommodityDto commodity;

	private UserDto user;

	public OrderDto() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public boolean isPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public CommodityDto getCommodity() {
		return commodity;
	}

	public void setCommodity(CommodityDto commodity) {
		this.commodity = commodity;
	}

	@Override
	public String toString() {
		return "OrderDto [id=" + id + ", quantity=" + quantity + ", commodity=" + commodity + ", user=" + user + "]";
	}

}
