package com.sombra.project.dao;

import com.sombra.project.entity.User;

public interface UserDao extends GenericDao<User> {

	User findByEmail(String email);

	User findByUuid(String uuid);

}
