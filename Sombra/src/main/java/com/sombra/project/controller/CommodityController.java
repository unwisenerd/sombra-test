package com.sombra.project.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sombra.project.dto.CommodityDto;
import com.sombra.project.service.CategoryService;
import com.sombra.project.service.CommodityService;
import com.sombra.project.service.SubCategoryService;
import com.sombra.project.validation.ValidationException;

@Controller
public class CommodityController {

	@Autowired
	private CommodityService commodityService;
	@Autowired
	private SubCategoryService subCategoryService;
	@Autowired
	private CategoryService categoryService;

	@RequestMapping(value = "/admin/commodities", method = RequestMethod.GET)
	public String adminCommodities(Model model) {
		model.addAttribute("commodities", commodityService.findAllWithSubCategory());
		model.addAttribute("subCategories", subCategoryService.findAll());
		return "admin-commodities";
	}

	@RequestMapping(value = "/admin/commodities/add", method = RequestMethod.POST)
	public @ResponseBody List<CommodityDto> adminCommoditiesAdd(@RequestBody CommodityDto commodityDto) {
		commodityDto.setSubCategory(subCategoryService.findOne(commodityDto.getSubCategory().getId()));
		commodityService.save(commodityDto);
		return commodityService.findAll();
	}

	@RequestMapping(value = "/admin/commodities/addFromCsv", method = RequestMethod.POST)
	public String adminCommoditiesAddFromCsv(@RequestParam MultipartFile csv) {
		commodityService.addFromCsv(csv);
		return "redirect:/admin/commodities";
	}

	@RequestMapping(value = "/admin/commodities/edit/{id}", method = RequestMethod.PUT)
	public @ResponseBody List<CommodityDto> adminCommoditiesEdit(@PathVariable int id,
			@RequestBody CommodityDto commodityDto) {
		commodityDto.setId(id);
		commodityDto.setSubCategory(subCategoryService.findOne(commodityDto.getSubCategory().getId()));
		commodityService.update(commodityDto);
		return commodityService.findAll();
	}

	@RequestMapping(value = "/admin/commodities/delete/{id}", method = RequestMethod.DELETE)
	public @ResponseBody List<CommodityDto> adminCommoditiesDelete(@PathVariable int id) {
		commodityService.delete(id);
		return commodityService.findAll();
	}

	@RequestMapping(value = "/admin/commodities/editImage/{id}", method = RequestMethod.POST)
	public @ResponseBody List<CommodityDto> adminCommoditiesEditImage(@PathVariable int id,
			@RequestParam MultipartFile image) {
		commodityService.editImage(id, image);
		return commodityService.findAll();
	}

	@RequestMapping(value = "/admin/commodities/deleteImage/{id}", method = RequestMethod.POST)
	public @ResponseBody List<CommodityDto> adminCommoditiesDeleteImage(@PathVariable int id) {
		commodityService.deleteImage(id);
		return commodityService.findAll();
	}

	@RequestMapping(value = "/commodities", method = RequestMethod.GET)
	public String commodities(@RequestParam(required = false) String category,
			@RequestParam(required = false) String subcategory, @RequestParam(required = false) String name,
			@RequestParam(required = false) String brand, @RequestParam(required = false) String country,
			@RequestParam(required = false) String consist, @RequestParam(required = false) String price,
			@RequestParam(required = false, defaultValue = "name") String order,
			@RequestParam(required = false, defaultValue = "asc") String direction,
			@RequestParam(required = false, defaultValue = "18") String count,
			@RequestParam(required = false, defaultValue = "0") String page, Model model) {
		model.addAttribute("commodities", commodityService.findByFilter(category, subcategory, name, brand, country,
				consist, price, order, direction, count, page));
		model.addAttribute("subCategories", subCategoryService.findAllWithCategory());
		model.addAttribute("categories", categoryService.findAll());
		model.addAttribute("brands", commodityService.findBrands());
		model.addAttribute("countries", commodityService.findCountries());
		model.addAttribute("minPrice", commodityService.findMinimumPrice());
		model.addAttribute("maxPrice", commodityService.findMaximumPrice());
		model.addAttribute("current", commodityService.getCurrentPage(page));
		model.addAttribute("pages", commodityService.countPagesByFilter(category, subcategory, name, brand, country,
				consist, price, count));
		return "commodities";
	}

	@RequestMapping(value = "/commodities/view/", method = RequestMethod.GET)
	public String commoditiesView() {
		return "redirect:/";
	}

	@RequestMapping(value = "/commodities/view/{id}", method = RequestMethod.GET)
	public String commoditiesView(@PathVariable int id,
			@CookieValue(value = "cart", required = false, defaultValue = "") String cart, Model model) {
		model.addAttribute("commodity", commodityService.findOne(id));
		model.addAttribute("cartQuantity", commodityService.getCartQuantity(id, cart));
		return "commodities-view";
	}

	@RequestMapping(value = "/commodities/view/{id}/addToCart", method = RequestMethod.POST)
	public @ResponseBody String commoditiesViewAddToCart(@PathVariable int id,
			@RequestParam(required = false, defaultValue = "1") int quantity,
			@CookieValue(value = "cart", required = false, defaultValue = "") String cart,
			HttpServletResponse response) {
		try {
			response.addCookie(commodityService.addToCart(id, quantity, cart));
			return "Added " + "<b>" + quantity + "</b>" + " items to cart";
		} catch (ValidationException e) {
			return e.getMessage();
		}
	}

	@RequestMapping(value = "/cart", method = RequestMethod.GET)
	public String cart(@CookieValue(value = "cart", required = false, defaultValue = "") String cart, Model model) {
		model.addAttribute("commodities", commodityService.getCartCommodities(cart).entrySet());
		return "cart";
	}

	@RequestMapping(value = "/cart/delete/{id}", method = RequestMethod.DELETE)
	public @ResponseBody Map<String, String> cartDelete(@PathVariable int id,
			@CookieValue(value = "cart", required = false, defaultValue = "") String cart, Model model,
			HttpServletResponse response) {
		Cookie cookie = commodityService.deleteFromCart(id, cart);
		response.addCookie(cookie);
		Map<CommodityDto, Integer> commodities = commodityService.getCartCommodities(cookie.getValue());
		return commodityService.cartCommoditiesToJson(commodities);
	}

}
