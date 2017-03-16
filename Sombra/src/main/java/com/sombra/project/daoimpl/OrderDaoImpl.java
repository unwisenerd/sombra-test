package com.sombra.project.daoimpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sombra.project.dao.OrderDao;
import com.sombra.project.entity.Order;

@Repository
public class OrderDaoImpl implements OrderDao {

	@PersistenceContext(unitName = "Sombra")
	private EntityManager entityManager;

	@Transactional
	public void save(Order order) {
		entityManager.persist(order);
	}

	@Transactional
	public void update(Order order) {
		entityManager.merge(order);
	}

	@Transactional
	public void delete(int id) {
		entityManager.remove(findOne(id));
	}

	public Order findOne(int id) {
		return entityManager.find(Order.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Order> findAll() {
		return (List<Order>) entityManager.createNamedQuery("Order.findAll").getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Order> findAllWithCommodityAndUser() {
		return (List<Order>) entityManager.createNamedQuery("Order.findAllWithCommodityAndUser").getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Order> findOrdersByUser(int id) {
		return (List<Order>) entityManager.createNamedQuery("Order.findOrdersByUser").setParameter("id", id)
				.getResultList();

	}

}
