package com.sombra.project.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.sombra.project.dto.UserDto;
import com.sombra.project.validation.ValidationException;

public interface UserService {

	void save(UserDto userDto);

	void update(UserDto userDto);

	void delete(int id);

	UserDto findOne(int id);

	UserDto findByEmail(String email);

	UserDto findByUuid(String uuid);

	List<UserDto> findAll();

	void register(UserDto userDto) throws ValidationException;

	void confirm(String uuid);

	void changeName(int id, String name) throws ValidationException;

	void changeSurname(int id, String surname) throws ValidationException;

	void changeEmail(int id, String email) throws ValidationException;

	void changePassword(int id, String password) throws ValidationException;

	void changePhone(int id, String phone) throws ValidationException;

	void changeImage(int id, MultipartFile multipartFile) throws ValidationException;

	void editImage(int id, MultipartFile multipartFile);

	void deleteImage(int id);

}
