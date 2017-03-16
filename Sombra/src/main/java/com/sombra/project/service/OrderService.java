package com.sombra.project.service;

import java.util.List;

import com.sombra.project.dto.OrderDto;
import com.sombra.project.validation.ValidationException;

public interface OrderService {

	void save(OrderDto orderDto);

	void update(OrderDto orderDto);

	void delete(int id);

	OrderDto findOne(int id);

	List<OrderDto> findAll();

	List<OrderDto> findAllWithCommodityAndUser();

	void makeOrder(int id, int userId, int quantity);

	List<OrderDto> findOrdersByUser(int id);

	void confirmPayment(int id, int userId, String card, String document) throws ValidationException;

}
