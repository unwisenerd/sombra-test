package com.sombra.project.service;

import java.util.List;

import com.sombra.project.dto.CategoryDto;

public interface CategoryService {

	void save(CategoryDto categoryDto);

	void update(CategoryDto categoryDto);

	void delete(int id);

	CategoryDto findOne(int id);

	List<CategoryDto> findAll();

}
