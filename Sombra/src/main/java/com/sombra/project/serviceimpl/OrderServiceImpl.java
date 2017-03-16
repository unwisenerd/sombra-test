package com.sombra.project.serviceimpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sombra.project.dao.CommodityDao;
import com.sombra.project.dao.OrderDao;
import com.sombra.project.dao.UserDao;
import com.sombra.project.dto.DtoMapper;
import com.sombra.project.dto.OrderDto;
import com.sombra.project.entity.Commodity;
import com.sombra.project.entity.Order;
import com.sombra.project.service.OrderService;
import com.sombra.project.validation.OrderValidator;
import com.sombra.project.validation.ValidationException;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao orderDao;
	@Autowired
	private CommodityDao commodityDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private OrderValidator orderValidator;

	public void save(OrderDto orderDto) {
		orderDao.save(DtoMapper.orderToEntity(orderDto));
	}

	public void update(OrderDto orderDto) {
		orderDao.update(DtoMapper.orderToEntity(orderDto));
	}

	public void delete(int id) {
		orderDao.delete(id);
		String absolutePath = System.getProperty("catalina.home") + "/resources/orders/" + id;
		File file = new File(absolutePath);
		if (file.exists()) {
			try {
				FileUtils.cleanDirectory(file);
				file.delete();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public OrderDto findOne(int id) {
		Order order = orderDao.findOne(id);
		if (order != null) {
			return DtoMapper.orderToDto(order);
		} else {
			return null;
		}
	}

	public List<OrderDto> findAll() {
		List<OrderDto> ordersDto = new ArrayList<>();
		for (Order order : orderDao.findAll()) {
			ordersDto.add(DtoMapper.orderToDto(order));
		}
		return ordersDto;
	}

	public List<OrderDto> findAllWithCommodityAndUser() {
		List<OrderDto> ordersDto = new ArrayList<>();
		List<Order> orders = orderDao.findAllWithCommodityAndUser();
		for (Order order : orders) {
			ordersDto.add(DtoMapper.orderToDto(order));
		}
		return ordersDto;
	}

	public void makeOrder(int id, int userId, int quantity) {
		Order order = new Order();
		order.setQuantity(quantity);
		order.setUser(userDao.findOne(userId));
		Commodity commodity = commodityDao.findOne(id);
		commodity.setAvailable(commodity.getAvailable() - quantity);
		order.setCommodity(commodity);
		commodityDao.update(commodity);
		orderDao.save(order);
	}

	public List<OrderDto> findOrdersByUser(int id) {
		List<OrderDto> ordersDto = new ArrayList<>();
		for (Order order : orderDao.findOrdersByUser(id)) {
			ordersDto.add(DtoMapper.orderToDto(order));
		}
		return ordersDto;
	}

	public void confirmPayment(int id, int userId, String card, String document) throws ValidationException {
		orderValidator.validatePayment(id, userId, card);
		Order order = orderDao.findOne(id);
		order.setPaid(true);
		order.setCard(card);
		order.setDocument(document);
		orderDao.update(order);

	}

}
