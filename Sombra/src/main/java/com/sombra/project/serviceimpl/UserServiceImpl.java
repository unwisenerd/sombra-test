package com.sombra.project.serviceimpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sombra.project.dao.UserDao;
import com.sombra.project.dto.DtoMapper;
import com.sombra.project.dto.UserDto;
import com.sombra.project.entity.Role;
import com.sombra.project.entity.User;
import com.sombra.project.service.UserService;
import com.sombra.project.validation.UserValidator;
import com.sombra.project.validation.ValidationException;

@Service(value = "userDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private UserValidator userValidator;

	public void save(UserDto userDto) {
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		User user = DtoMapper.userToEntity(userDto);
		userDao.save(user);
	}

	public void update(UserDto userDto) {
		User oldUser = userDao.findOne(userDto.getId());
		userDto.setPassword(oldUser.getPassword());
		userDto.setUuid(oldUser.getUuid());
		userDto.setImage(oldUser.getImage());
		User user = DtoMapper.userToEntity(userDto);
		userDao.update(user);
	}

	public void delete(int id) {
		userDao.delete(id);
		String absolutePath = System.getProperty("catalina.home") + "/resources/users/" + id;
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

	public UserDto findOne(int id) {
		User user = userDao.findOne(id);
		if (user != null) {
			return DtoMapper.userToDto(user);
		} else {
			return null;
		}
	}

	public UserDto findByEmail(String email) {
		User user = userDao.findByEmail(email);
		if (user != null) {
			return DtoMapper.userToDto(user);
		} else {
			return null;
		}
	}

	public UserDto findByUuid(String uuid) {
		User user = userDao.findByUuid(uuid);
		if (user != null) {
			return DtoMapper.userToDto(user);
		} else {
			return null;
		}
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return findByEmail(username);
	}

	public List<UserDto> findAll() {
		List<UserDto> usersDto = new ArrayList<>();
		for (User user : userDao.findAll()) {
			usersDto.add(DtoMapper.userToDto(user));
		}
		return usersDto;
	}

	public void register(UserDto userDto) throws ValidationException {
		userValidator.validateName(userDto.getName());
		userValidator.validateSurname(userDto.getSurname());
		userValidator.validateEmail(userDto.getEmail());
		userValidator.validatePassword(userDto.getPassword());
		userValidator.validatePhone(userDto.getPhone());
		userDto.setRole(Role.ROLE_USER);
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		User user = DtoMapper.userToEntity(userDto);
		userDao.save(user);
	}

	public void confirm(String uuid) {
		User user = userDao.findByUuid(uuid);
		user.setEnabled(true);
		userDao.update(user);
	}

	public void changeName(int id, String name) throws ValidationException {
		User user = userDao.findOne(id);
		userValidator.validateName(name);
		user.setName(name);
		userDao.update(user);
	}

	public void changeSurname(int id, String surname) throws ValidationException {
		User user = userDao.findOne(id);
		userValidator.validateSurname(surname);
		user.setSurname(surname);
		userDao.update(user);
	}

	public void changeEmail(int id, String email) throws ValidationException {
		User user = userDao.findOne(id);
		userValidator.validateEmail(email);
		user.setEmail(email);
		userDao.update(user);
	}

	public void changePassword(int id, String password) throws ValidationException {
		User user = userDao.findOne(id);
		userValidator.validatePassword(password);
		user.setPassword(passwordEncoder.encode(password));
		userDao.update(user);
	}

	public void changePhone(int id, String phone) throws ValidationException {
		User user = userDao.findOne(id);
		userValidator.validatePhone(phone);
		user.setPhone(phone);
		userDao.update(user);
	}

	public void changeImage(int id, MultipartFile multipartFile) throws ValidationException {
		userValidator.validateImage(multipartFile);
		User user = userDao.findOne(id);
		String originalFileName = multipartFile.getOriginalFilename();
		String relativePath = "/resources/users/" + user.getId();
		String absolutePath = System.getProperty("catalina.home") + relativePath;
		String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."), originalFileName.length())
				.toLowerCase();
		String relativeFileName = relativePath + "/img" + fileExtension;
		String absoluteFileName = absolutePath + "/img" + fileExtension;
		user.setImage(relativeFileName);
		userDao.update(user);
		File file = new File(absolutePath);
		try {
			file.mkdirs();
			FileUtils.cleanDirectory(file);
			multipartFile.transferTo(new File(absoluteFileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void editImage(int id, MultipartFile multipartFile) {
		User user = userDao.findOne(id);
		String originalFileName = multipartFile.getOriginalFilename();
		String relativePath = "/resources/users/" + user.getId();
		String absolutePath = System.getProperty("catalina.home") + relativePath;
		String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."), originalFileName.length())
				.toLowerCase();
		String relativeFileName = relativePath + "/img" + fileExtension;
		String absoluteFileName = absolutePath + "/img" + fileExtension;
		user.setImage(relativeFileName);
		userDao.update(user);
		File file = new File(absolutePath);
		try {
			file.mkdirs();
			FileUtils.cleanDirectory(file);
			multipartFile.transferTo(new File(absoluteFileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deleteImage(int id) {
		User user = userDao.findOne(id);
		user.setImage(null);
		userDao.update(user);
		String absolutePath = System.getProperty("catalina.home") + "/resources/users/" + id;
		File file = new File(absolutePath);
		if (file.exists()) {
			try {
				FileUtils.cleanDirectory(new File(absolutePath));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
