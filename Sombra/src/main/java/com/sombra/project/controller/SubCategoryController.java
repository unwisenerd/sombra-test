package com.sombra.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sombra.project.dto.SubCategoryDto;
import com.sombra.project.service.CategoryService;
import com.sombra.project.service.SubCategoryService;

@Controller
public class SubCategoryController {

	@Autowired
	private SubCategoryService subCategoryService;
	@Autowired
	private CategoryService categoryService;

	@RequestMapping(value = "/admin/subcategories", method = RequestMethod.GET)
	public String adminSubCategories(Model model) {
		model.addAttribute("subCategories", subCategoryService.findAllWithCategory());
		model.addAttribute("categories", categoryService.findAll());
		return "admin-subcategories";
	}

	@RequestMapping(value = "/admin/subcategories/add", method = RequestMethod.POST)
	public @ResponseBody List<SubCategoryDto> adminSubCategoriesAdd(@RequestBody SubCategoryDto subCategoryDto) {
		subCategoryDto.setCategory(categoryService.findOne(subCategoryDto.getCategory().getId()));
		subCategoryService.save(subCategoryDto);
		return subCategoryService.findAll();
	}

	@RequestMapping(value = "/admin/subcategories/edit/{id}", method = RequestMethod.PUT)
	public @ResponseBody List<SubCategoryDto> adminSubCategoriesEdit(@PathVariable int id,
			@RequestBody SubCategoryDto subCategoryDto) {
		subCategoryDto.setId(id);
		subCategoryDto.setCategory(categoryService.findOne(subCategoryDto.getCategory().getId()));
		subCategoryService.update(subCategoryDto);
		return subCategoryService.findAll();
	}

	@RequestMapping(value = "/admin/subcategories/delete/{id}", method = RequestMethod.DELETE)
	public @ResponseBody List<SubCategoryDto> adminSubCategoriesDelete(@PathVariable int id) {
		subCategoryService.delete(id);
		return subCategoryService.findAll();
	}

}
