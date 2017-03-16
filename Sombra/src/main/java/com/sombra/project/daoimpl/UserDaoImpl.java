package com.sombra.project.daoimpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sombra.project.dao.UserDao;
import com.sombra.project.entity.User;

@Repository
public class UserDaoImpl implements UserDao {

	@PersistenceContext(unitName = "Sombra")
	private EntityManager entityManager;

	@Transactional
	public void save(User user) {
		entityManager.persist(user);
	}

	@Transactional
	public void update(User user) {
		entityManager.merge(user);
	}

	@Transactional
	public void delete(int id) {
		entityManager.remove(findOne(id));
	}

	public User findOne(int id) {
		return entityManager.find(User.class, id);
	}

	public User findByEmail(String email) {
		try {
			return (User) entityManager.createNamedQuery("User.findByEmail").setParameter("email", email)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public User findByUuid(String uuid) {
		try {
			return (User) entityManager.createNamedQuery("User.findByUuid").setParameter("uuid", uuid)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<User> findAll() {
		return (List<User>) entityManager.createNamedQuery("User.findAll").getResultList();
	}

}
