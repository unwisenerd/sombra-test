package com.sombra.project.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
@NamedQueries({ @NamedQuery(name = "Order.findAll", query = "SELECT o FROM Order o"),
		@NamedQuery(name = "Order.findAllWithCommodityAndUser", query = "SELECT DISTINCT o FROM Order o LEFT JOIN FETCH o.commodity LEFT JOIN FETCH o.user"),
		@NamedQuery(name = "Order.findOrdersByUser", query = "SELECT DISTINCT o FROM Order o LEFT JOIN FETCH o.commodity LEFT JOIN FETCH o.user WHERE o.user.id = :id") })
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int quantity;
	private boolean paid;
	private String card;
	private String document;

	@ManyToOne
	@JoinColumn(name = "commodity")
	private Commodity commodity;

	@ManyToOne
	@JoinColumn(name = "user")
	private User user;

	public Order() {

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Commodity getCommodity() {
		return commodity;
	}

	public void setCommodity(Commodity commodity) {
		this.commodity = commodity;
	}

}
