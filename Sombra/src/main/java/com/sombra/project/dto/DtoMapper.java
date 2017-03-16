package com.sombra.project.dto;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;

import com.sombra.project.entity.Category;
import com.sombra.project.entity.Commodity;
import com.sombra.project.entity.Order;
import com.sombra.project.entity.SubCategory;
import com.sombra.project.entity.User;

public class DtoMapper {

	public static CategoryDto categoryToDto(Category category) {
		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setId(category.getId());
		categoryDto.setName(category.getName());
		if (Hibernate.isInitialized(category.getSubCategories()) && (category.getSubCategories() != null)) {
			List<SubCategoryDto> subCategoriesDto = new ArrayList<>();
			List<SubCategory> subCategories = category.getSubCategories();
			for (SubCategory subCategory : subCategories) {
				subCategoriesDto.add(subCategoryToDto(subCategory));
			}
			categoryDto.setSubCategories(subCategoriesDto);
		}
		return categoryDto;
	}

	public static Category categoryToEntity(CategoryDto categoryDto) {
		Category category = new Category();
		category.setId(categoryDto.getId());
		category.setName(categoryDto.getName());
		if (categoryDto.getSubCategories() != null) {
			List<SubCategory> subCategories = new ArrayList<>();
			List<SubCategoryDto> subCategoriesDto = categoryDto.getSubCategories();
			for (SubCategoryDto subCategoryDto : subCategoriesDto) {
				subCategories.add(subCategoryToEntity(subCategoryDto));
			}
			category.setSubCategories(subCategories);
		}
		return category;
	}

	public static CommodityDto commodityToDto(Commodity commodity) {
		CommodityDto commodityDto = new CommodityDto();
		commodityDto.setId(commodity.getId());
		commodityDto.setName(commodity.getName());
		commodityDto.setBrand(commodity.getBrand());
		commodityDto.setCountry(commodity.getCountry());
		commodityDto.setConsist(commodity.getConsist());
		commodityDto.setPrice(commodity.getPrice());
		commodityDto.setAvailable(commodity.getAvailable());
		commodityDto.setImage(commodity.getImage());
		if (Hibernate.isInitialized(commodity.getSubCategory()) && (commodity.getSubCategory() != null)) {
			commodityDto.setSubCategory(subCategoryToDto(commodity.getSubCategory()));
		}
		if (Hibernate.isInitialized(commodity.getOrders()) && (commodity.getOrders() != null)) {
			List<OrderDto> ordersDto = new ArrayList<>();
			List<Order> orders = commodity.getOrders();
			for (Order order : orders) {
				ordersDto.add(orderToDto(order));
			}
			commodityDto.setOrders(ordersDto);
		}
		return commodityDto;
	}

	public static Commodity commodityToEntity(CommodityDto commodityDto) {
		Commodity commodity = new Commodity();
		commodity.setId(commodityDto.getId());
		commodity.setName(commodityDto.getName());
		commodity.setBrand(commodityDto.getBrand());
		commodity.setCountry(commodityDto.getCountry());
		commodity.setConsist(commodityDto.getConsist());
		commodity.setPrice(commodityDto.getPrice());
		commodity.setAvailable(commodityDto.getAvailable());
		commodity.setImage(commodityDto.getImage());
		if (commodityDto.getSubCategory() != null) {
			commodity.setSubCategory(subCategoryToEntity(commodityDto.getSubCategory()));
		}
		if (commodityDto.getOrders() != null) {
			List<Order> orders = new ArrayList<>();
			List<OrderDto> ordersDto = commodityDto.getOrders();
			for (OrderDto orderDto : ordersDto) {
				orders.add(orderToEntity(orderDto));
			}
			commodity.setOrders(orders);
		}
		return commodity;
	}

	public static OrderDto orderToDto(Order order) {
		OrderDto orderDto = new OrderDto();
		orderDto.setId(order.getId());
		orderDto.setQuantity(order.getQuantity());
		orderDto.setPaid(order.isPaid());
		orderDto.setCard(order.getCard());
		orderDto.setDocument(order.getDocument());
		if (Hibernate.isInitialized(order.getUser()) && (order.getUser() != null)) {
			orderDto.setUser(userToDto(order.getUser()));
		}
		if (Hibernate.isInitialized(order.getCommodity()) && (order.getCommodity() != null)) {
			orderDto.setCommodity(commodityToDto(order.getCommodity()));
		}
		return orderDto;
	}

	public static Order orderToEntity(OrderDto orderDto) {
		Order order = new Order();
		order.setId(orderDto.getId());
		order.setQuantity(orderDto.getQuantity());
		order.setPaid(orderDto.isPaid());
		order.setCard(orderDto.getCard());
		order.setDocument(orderDto.getDocument());
		if (orderDto.getUser() != null) {
			order.setUser(userToEntity(orderDto.getUser()));
		}
		if (orderDto.getCommodity() != null) {
			order.setCommodity(commodityToEntity(orderDto.getCommodity()));
		}
		return order;
	}

	public static SubCategoryDto subCategoryToDto(SubCategory subCategory) {
		SubCategoryDto subCategoryDto = new SubCategoryDto();
		subCategoryDto.setId(subCategory.getId());
		subCategoryDto.setName(subCategory.getName());
		if (Hibernate.isInitialized(subCategory.getCategory()) && (subCategory.getCategory() != null)) {
			subCategoryDto.setCategory(categoryToDto(subCategory.getCategory()));
		}
		if (Hibernate.isInitialized(subCategory.getCommodities()) && (subCategory.getCommodities() != null)) {
			List<CommodityDto> commoditiesDto = new ArrayList<>();
			List<Commodity> commodities = subCategory.getCommodities();
			for (Commodity commodity : commodities) {
				commoditiesDto.add(commodityToDto(commodity));
			}
			subCategoryDto.setCommodities(commoditiesDto);
		}
		return subCategoryDto;
	}

	public static SubCategory subCategoryToEntity(SubCategoryDto subCategoryDto) {
		SubCategory subCategory = new SubCategory();
		subCategory.setId(subCategoryDto.getId());
		subCategory.setName(subCategoryDto.getName());
		if (subCategoryDto.getCategory() != null) {
			subCategory.setCategory(categoryToEntity(subCategoryDto.getCategory()));
		}
		if (subCategoryDto.getCommodities() != null) {
			List<Commodity> commodities = new ArrayList<>();
			List<CommodityDto> commoditiesDto = subCategoryDto.getCommodities();
			for (CommodityDto commodityDto : commoditiesDto) {
				commodities.add(commodityToEntity(commodityDto));
			}
			subCategory.setCommodities(commodities);
		}
		return subCategory;
	}

	public static UserDto userToDto(User user) {
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setName(user.getName());
		userDto.setSurname(user.getSurname());
		userDto.setEmail(user.getEmail());
		userDto.setPassword(user.getPassword());
		userDto.setPhone(user.getPhone());
		userDto.setLocked(user.isLocked());
		userDto.setEnabled(user.isEnabled());
		userDto.setUuid(user.getUuid());
		userDto.setRole(user.getRole());
		userDto.setImage(user.getImage());
		if (Hibernate.isInitialized(user.getOrders()) && (user.getOrders() != null)) {
			List<OrderDto> ordersDto = new ArrayList<>();
			List<Order> orders = user.getOrders();
			for (Order order : orders) {
				ordersDto.add(orderToDto(order));
			}
			userDto.setOrders(ordersDto);
		}
		return userDto;
	}

	public static User userToEntity(UserDto userDto) {
		User user = new User();
		user.setId(userDto.getId());
		user.setName(userDto.getName());
		user.setSurname(userDto.getSurname());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setPhone(userDto.getPhone());
		user.setLocked(userDto.isLocked());
		user.setEnabled(userDto.isEnabled());
		user.setUuid(userDto.getUuid());
		user.setImage(userDto.getImage());
		user.setRole(userDto.getRole());
		user.setImage(userDto.getImage());
		if (userDto.getOrders() != null) {
			List<Order> orders = new ArrayList<>();
			List<OrderDto> ordersDto = userDto.getOrders();
			for (OrderDto orderDto : ordersDto) {
				orders.add(orderToEntity(orderDto));
			}
			user.setOrders(orders);
		}
		return user;
	}

}
