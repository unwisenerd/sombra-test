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
@Table(name = "subcategories")
@NamedQueries({ @NamedQuery(name = "SubCategory.findAll", query = "SELECT s FROM SubCategory s"),
		@NamedQuery(name = "SubCategory.findAllWithCategory", query = "SELECT DISTINCT s FROM SubCategory s LEFT JOIN FETCH s.category") })
public class SubCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;

	@ManyToOne
	@JoinColumn(name = "category")
	private Category category;

	@OneToMany(mappedBy = "subCategory", cascade = CascadeType.REMOVE)
	private List<Commodity> commodities;

	public SubCategory() {

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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Commodity> getCommodities() {
		return commodities;
	}

	public void setCommodities(List<Commodity> commodities) {
		this.commodities = commodities;
	}

}
