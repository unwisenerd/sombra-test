package com.sombra.project.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.springframework.web.multipart.MultipartFile;

import com.sombra.project.dto.CommodityDto;
import com.sombra.project.validation.ValidationException;

public interface CommodityService {

	void save(CommodityDto commodityDto);

	void update(CommodityDto commodityDto);

	void delete(int id);

	CommodityDto findOne(int id);

	List<CommodityDto> findAll();

	List<CommodityDto> findAllWithSubCategory();

	List<String> findBrands();

	List<String> findCountries();

	int findMinimumPrice();

	int findMaximumPrice();

	List<CommodityDto> findByFilter(String category, String subCategory, String name, String brand, String country,
			String consist, String price, String order, String direction, String count, String page);

	int countPagesByFilter(String category, String subCategory, String name, String brand, String country,
			String consist, String price, String count);

	int getCurrentPage(String page);

	void editImage(int id, MultipartFile multipartFile);

	void deleteImage(int id);

	Cookie addToCart(int id, int quantity, String cart) throws ValidationException;

	int getCartQuantity(int id, String cart);

	Map<CommodityDto, Integer> getCartCommodities(String cart);

	Map<String, String> cartCommoditiesToJson(Map<CommodityDto, Integer> commodities);

	Cookie deleteFromCart(int id, String cart);

	void addFromCsv(MultipartFile multipartFile);

}
