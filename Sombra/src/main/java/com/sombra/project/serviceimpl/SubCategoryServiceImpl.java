package com.sombra.project.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sombra.project.dao.SubCategoryDao;
import com.sombra.project.dto.DtoMapper;
import com.sombra.project.dto.SubCategoryDto;
import com.sombra.project.entity.SubCategory;
import com.sombra.project.service.SubCategoryService;

@Service
public class SubCategoryServiceImpl implements SubCategoryService {

	@Autowired
	private SubCategoryDao subCategoryDao;

	public void save(SubCategoryDto subCategoryDto) {
		subCategoryDao.save(DtoMapper.subCategoryToEntity(subCategoryDto));
	}

	public void update(SubCategoryDto subCategoryDto) {
		subCategoryDao.update(DtoMapper.subCategoryToEntity(subCategoryDto));
	}

	public void delete(int id) {
		subCategoryDao.delete(id);
	}

	public SubCategoryDto findOne(int id) {
		SubCategory subCategory = subCategoryDao.findOne(id);
		if (subCategory != null) {
			return DtoMapper.subCategoryToDto(subCategory);
		} else {
			return null;
		}
	}

	public List<SubCategoryDto> findAll() {
		List<SubCategoryDto> subCategoriesDto = new ArrayList<>();
		for (SubCategory subCategory : subCategoryDao.findAll()) {
			subCategoriesDto.add(DtoMapper.subCategoryToDto(subCategory));
		}
		return subCategoriesDto;
	}

	public List<SubCategoryDto> findAllWithCategory() {
		List<SubCategoryDto> subCategoriesDto = new ArrayList<>();
		List<SubCategory> subCategories = subCategoryDao.findAllWithCategory();
		for (SubCategory subCategory : subCategories) {
			subCategoriesDto.add(DtoMapper.subCategoryToDto(subCategory));
		}
		return subCategoriesDto;
	}

}
