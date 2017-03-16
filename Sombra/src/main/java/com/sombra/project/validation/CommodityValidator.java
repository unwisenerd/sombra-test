package com.sombra.project.validation;

public interface CommodityValidator {

	void validateQuantity(int id, int quantity, String cart) throws ValidationException;

}
