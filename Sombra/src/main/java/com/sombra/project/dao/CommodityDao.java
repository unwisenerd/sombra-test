package com.sombra.project.dao;

import java.util.List;

import com.sombra.project.entity.Commodity;

public interface CommodityDao extends GenericDao<Commodity> {

	List<Commodity> findAllWithSubCategory();

	List<String> findBrands();

	List<String> findCountries();

	int findMinimumPrice();

	int findMaximumPrice();

	List<Commodity> findByFilter(String category, String subCategory, String name, String brand, String country,
			String consist, String price, String order, String direction, String count, String page);

	long countByFilter(String category, String subCategory, String name, String brand, String country, String consist,
			String price);

}
