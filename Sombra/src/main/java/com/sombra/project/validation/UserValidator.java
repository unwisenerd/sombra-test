package com.sombra.project.validation;

import org.springframework.web.multipart.MultipartFile;

public interface UserValidator {

	void validateName(String name) throws ValidationException;

	void validateSurname(String surname) throws ValidationException;

	void validateEmail(String email) throws ValidationException;

	void validatePassword(String password) throws ValidationException;

	void validatePhone(String phone) throws ValidationException;

	void validateImage(MultipartFile multipartFile) throws ValidationException;

}
