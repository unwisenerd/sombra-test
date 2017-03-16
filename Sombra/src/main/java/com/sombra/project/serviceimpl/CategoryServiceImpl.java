package com.sombra.project.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sombra.project.dao.CategoryDao;
import com.sombra.project.dto.CategoryDto;
import com.sombra.project.dto.DtoMapper;
import com.sombra.project.entity.Category;
import com.sombra.project.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryDao categoryDao;

	public void save(CategoryDto categoryDto) {
		Category category = DtoMapper.categoryToEntity(categoryDto);
		categoryDao.save(category);
	}

	public void update(CategoryDto categoryDto) {
		categoryDao.update(DtoMapper.categoryToEntity(categoryDto));
	}

	public void delete(int id) {
		categoryDao.delete(id);
	}

	public CategoryDto findOne(int id) {
		Category category = categoryDao.findOne(id);
		if (category != null) {
			return DtoMapper.categoryToDto(categoryDao.findOne(id));
		} else {
			return null;
		}
	}

	public List<CategoryDto> findAll() {
		List<CategoryDto> categoriesDto = new ArrayList<>();
		for (Category category : categoryDao.findAll()) {
			categoriesDto.add(DtoMapper.categoryToDto(category));
		}
		return categoriesDto;
	}

}
