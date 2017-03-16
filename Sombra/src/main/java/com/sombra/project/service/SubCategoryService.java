package com.sombra.project.service;

import java.util.List;

import com.sombra.project.dto.SubCategoryDto;

public interface SubCategoryService {

	void save(SubCategoryDto subCategoryDto);

	void update(SubCategoryDto subCategoryDto);

	void delete(int id);

	SubCategoryDto findOne(int id);

	List<SubCategoryDto> findAll();

	List<SubCategoryDto> findAllWithCategory();

}
