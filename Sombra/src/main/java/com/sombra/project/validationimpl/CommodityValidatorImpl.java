package com.sombra.project.validationimpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sombra.project.dao.CommodityDao;
import com.sombra.project.entity.Commodity;
import com.sombra.project.validation.CommodityValidator;
import com.sombra.project.validation.ValidationException;

@Component
public class CommodityValidatorImpl implements CommodityValidator {

	@Autowired
	private CommodityDao commodityDao;

	@SuppressWarnings("unchecked")
	public void validateQuantity(int id, int quantity, String cart) throws ValidationException {
		Commodity commodity = commodityDao.findOne(id);
		ObjectMapper objectMapper = new ObjectMapper();
		int cartQuantity = 0;
		if (!cart.isEmpty()) {
			try {
				Map<String, Integer> commodities = (Map<String, Integer>) objectMapper.readValue(cart, Map.class);
				if (commodities.containsKey(String.valueOf(id))) {
					cartQuantity = commodities.get(String.valueOf(id));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (quantity + cartQuantity > commodity.getAvailable()) {
			throw new ValidationException("Commodity is not available");
		}
		if (quantity <= 0) {
			throw new ValidationException("Invalid quantity");
		}
	}

}
