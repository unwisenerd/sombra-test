package com.sombra.project.dao;

import java.util.List;

import com.sombra.project.entity.Order;

public interface OrderDao extends GenericDao<Order> {

	List<Order> findAllWithCommodityAndUser();

	List<Order> findOrdersByUser(int id);

}
