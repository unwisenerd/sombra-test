package com.sombra.project.dto;

import java.util.List;

import com.sombra.project.dto.CategoryDto;
import com.sombra.project.dto.CommodityDto;

public class SubCategoryDto {

	private int id;
	private String name;

	private CategoryDto category;

	private List<CommodityDto> commodities;

	public SubCategoryDto() {

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

	public CategoryDto getCategory() {
		return category;
	}

	public void setCategory(CategoryDto category) {
		this.category = category;
	}

	public List<CommodityDto> getCommodities() {
		return commodities;
	}

	public void setCommodities(List<CommodityDto> commodities) {
		this.commodities = commodities;
	}

	@Override
	public String toString() {
		return "SubCategoryDto [id=" + id + ", name=" + name + ", category=" + category + ", commodities=" + commodities
				+ "]";
	}

}
