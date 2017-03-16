package com.sombra.project.validationimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.sombra.project.dao.UserDao;
import com.sombra.project.validation.UserValidator;
import com.sombra.project.validation.ValidationException;

@Component
public class UserValidatorImpl implements UserValidator {

	@Autowired
	private UserDao userDao;

	public void validateName(String name) throws ValidationException {
		if ((!name.isEmpty()) && (name.matches("[^А-Яа-яA-Za-zІіЇїЄєЁё]+"))) {
			throw new ValidationException("Invalid name");
		}
	}

	public void validateSurname(String surname) throws ValidationException {
		if ((!surname.isEmpty()) && (surname.matches("[^А-Яа-яA-Za-zІіЇїЄєЁё]+"))) {
			throw new ValidationException("Invalid surname");
		}
	}

	public void validateEmail(String email) throws ValidationException {
		if ((email.isEmpty()) || (!email.matches("\\w+@\\w+\\.\\w+"))) {
			throw new ValidationException("Invalid e-mail");
		}
		if ((userDao.findByEmail(email) != null)) {
			throw new ValidationException("E-mail already exists");
		}

	}

	public void validatePassword(String password) throws ValidationException {
		if (password.isEmpty()) {
			throw new ValidationException("Invalid password");
		}
	}

	public void validatePhone(String phone) throws ValidationException {
		if ((phone.isEmpty()) || (!phone.matches("\\+?\\d+"))) {
			throw new ValidationException("Invalid phone");
		}
	}

	public void validateImage(MultipartFile multipartFile) throws ValidationException {
		if (multipartFile == null) {
			throw new ValidationException("Invalid image");
		}
		String fileName = multipartFile.getOriginalFilename();
		String fileExtension = fileName.substring(fileName.indexOf("."), fileName.length()).toLowerCase();
		if ((!fileExtension.equals(".jpg")) && (!fileExtension.equals(".gif")) && (!fileExtension.equals(".png"))) {
			throw new ValidationException("Invalid image");
		}
	}

}
