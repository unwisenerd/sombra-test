package com.sombra.project.daoimpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sombra.project.entity.Category;
import com.sombra.project.dao.CategoryDao;

@Repository
public class CategoryDaoImpl implements CategoryDao {

	@PersistenceContext(unitName = "Sombra")
	private EntityManager entityManager;

	@Transactional
	public void save(Category category) {
		entityManager.persist(category);
	}

	@Transactional
	public void update(Category category) {
		entityManager.merge(category);
	}

	@Transactional
	public void delete(int id) {
		entityManager.remove(findOne(id));
	}

	public Category findOne(int id) {
		return entityManager.find(Category.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Category> findAll() {
		return (List<Category>) entityManager.createNamedQuery("Category.findAll").getResultList();
	}

}
