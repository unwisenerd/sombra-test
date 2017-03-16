package com.sombra.project.daoimpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sombra.project.dao.SubCategoryDao;
import com.sombra.project.entity.SubCategory;

@Repository
public class SubCategoryDaoImpl implements SubCategoryDao {

	@PersistenceContext(unitName = "Sombra")
	private EntityManager entityManager;

	@Transactional
	public void save(SubCategory subCategory) {
		entityManager.persist(subCategory);
	}

	@Transactional
	public void update(SubCategory subCategory) {
		entityManager.merge(subCategory);
	}

	@Transactional
	public void delete(int id) {
		entityManager.remove(findOne(id));
	}

	public SubCategory findOne(int id) {
		return entityManager.find(SubCategory.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<SubCategory> findAll() {
		return (List<SubCategory>) entityManager.createNamedQuery("SubCategory.findAll").getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<SubCategory> findAllWithCategory() {
		return (List<SubCategory>) entityManager.createNamedQuery("SubCategory.findAllWithCategory").getResultList();
	}

}
