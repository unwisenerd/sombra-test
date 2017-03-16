package com.sombra.project.validation;

public interface OrderValidator {

	void validatePayment(int id, int userId, String card) throws ValidationException;

}
