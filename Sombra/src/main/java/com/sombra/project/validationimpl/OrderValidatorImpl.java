package com.sombra.project.validationimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sombra.project.dao.OrderDao;
import com.sombra.project.entity.Order;
import com.sombra.project.validation.OrderValidator;
import com.sombra.project.validation.ValidationException;

@Component
public class OrderValidatorImpl implements OrderValidator {

	@Autowired
	private OrderDao orderDao;

	public void validatePayment(int id, int userId, String card) throws ValidationException {
		Order order = orderDao.findOne(id);
		if (order.getUser().getId() != userId) {
			throw new ValidationException("Invalid order");
		}
		if ((card.isEmpty()) || (!card.matches("[0-9]{16}"))) {
			throw new ValidationException("Invalid card number");
		}
	}

}
