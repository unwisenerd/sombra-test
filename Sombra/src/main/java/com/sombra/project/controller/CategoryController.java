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

import com.sombra.project.dto.CategoryDto;
import com.sombra.project.service.CategoryService;

@Controller
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@RequestMapping(value = "/admin/categories", method = RequestMethod.GET)
	public String commodities(Model model) {
		model.addAttribute("categories", categoryService.findAll());
		return "admin-categories";
	}

	@RequestMapping(value = "/admin/categories/add", method = RequestMethod.POST)
	public @ResponseBody List<CategoryDto> add(@RequestBody CategoryDto categoryDto) {
		categoryService.save(categoryDto);
		return categoryService.findAll();
	}

	@RequestMapping(value = "/admin/categories/edit/{id}", method = RequestMethod.PUT)
	public @ResponseBody List<CategoryDto> edit(@PathVariable int id, @RequestBody CategoryDto categoryDto) {
		categoryDto.setId(id);
		categoryService.update(categoryDto);
		return categoryService.findAll();
	}

	@RequestMapping(value = "/admin/categories/delete/{id}", method = RequestMethod.DELETE)
	public @ResponseBody List<CategoryDto> delete(@PathVariable int id) {
		categoryService.delete(id);
		return categoryService.findAll();
	}

}
