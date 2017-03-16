package com.sombra.project.dao;

import java.util.List;

public interface GenericDao<T> {

	void save(T t);

	void update(T t);

	void delete(int id);

	T findOne(int id);

	List<T> findAll();

}
