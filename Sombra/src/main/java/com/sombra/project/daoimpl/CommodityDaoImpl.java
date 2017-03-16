package com.sombra.project.daoimpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sombra.project.dao.CommodityDao;
import com.sombra.project.entity.Commodity;

@Repository
public class CommodityDaoImpl implements CommodityDao {

	@PersistenceContext(unitName = "Sombra")
	private EntityManager entityManager;

	@Transactional
	public void save(Commodity commodity) {
		entityManager.persist(commodity);
	}

	@Transactional
	public void update(Commodity commodity) {
		entityManager.merge(commodity);
	}

	@Transactional
	public void delete(int id) {
		entityManager.remove(findOne(id));
	}

	public Commodity findOne(int id) {
		return entityManager.find(Commodity.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Commodity> findAll() {
		return (List<Commodity>) entityManager.createNamedQuery("Commodity.findAll").getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Commodity> findAllWithSubCategory() {
		return (List<Commodity>) entityManager.createNamedQuery("Commodity.findAllWithSubCategory").getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<String> findBrands() {
		return (List<String>) entityManager.createNamedQuery("Commodity.findBrands").getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<String> findCountries() {
		return (List<String>) entityManager.createNamedQuery("Commodity.findCountries").getResultList();
	}

	public int findMinimumPrice() {
		return (int) entityManager.createNamedQuery("Commodity.findMinimumPrice").getSingleResult();
	}

	public int findMaximumPrice() {
		return (int) entityManager.createNamedQuery("Commodity.findMaximumPrice").getSingleResult();
	}

	public List<Commodity> findByFilter(String category, String subCategory, String name, String brand, String country,
			String consist, String price, String order, String direction, String count, String page) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Commodity> criteriaQuery = criteriaBuilder.createQuery(Commodity.class);
		Root<Commodity> root = criteriaQuery.from(Commodity.class);
		List<Predicate> predicates = new ArrayList<>();
		if ((category != null) && (!category.isEmpty()) && (category.matches("^[0-9]+$"))) {
			predicates.add(criteriaBuilder.equal(
					root.join("subCategory", JoinType.LEFT).join("category", JoinType.LEFT).get("id"),
					Integer.parseInt(category)));
		}
		if ((subCategory != null) && (!subCategory.isEmpty()) && (subCategory.matches("^[0-9]+$"))) {
			predicates.add(criteriaBuilder.equal(root.join("subCategory", JoinType.LEFT).get("id"),
					Integer.parseInt(subCategory)));
		}
		if ((name != null) && (!name.isEmpty())) {
			predicates
					.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("name")), "%" + name.toUpperCase() + "%"));
		}
		if ((brand != null) && (!brand.isEmpty())) {
			predicates.add(
					criteriaBuilder.like(criteriaBuilder.upper(root.get("brand")), "%" + brand.toUpperCase() + "%"));
		}
		if ((country != null) && (!country.isEmpty())) {
			predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("country")),
					"%" + country.toUpperCase() + "%"));
		}
		if ((consist != null) && (!consist.isEmpty())) {
			predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("consist")),
					"%" + consist.toUpperCase() + "%"));
		}
		if ((price != null) && (!price.isEmpty()) && (price.matches("^[0-9]+$"))) {
			predicates.add(criteriaBuilder.le(root.get("price"), Integer.parseInt(price)));
		}
		criteriaQuery.where(predicates.toArray(new Predicate[] {}));
		try {
			switch (direction.toLowerCase()) {
			case "desc":
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get(order)));
				break;
			case "asc":
			default:
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get(order)));
				break;
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		int currentPage = 0;
		if (page.matches("^[0-9]+$")) {
			currentPage = Integer.parseInt(page);
		}
		int itemCount = 18;
		if (count.matches("^[0-9]+$")) {
			itemCount = Integer.parseInt(count);
		}
		return entityManager.createQuery(criteriaQuery).setFirstResult(currentPage * itemCount).setMaxResults(itemCount)
				.getResultList();
	}

	public long countByFilter(String category, String subCategory, String name, String brand, String country,
			String consist, String price) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<Commodity> root = criteriaQuery.from(Commodity.class);
		List<Predicate> predicates = new ArrayList<>();
		if ((category != null) && (!category.isEmpty()) && (category.matches("^[0-9]+$"))) {
			predicates.add(criteriaBuilder.equal(
					root.join("subCategory", JoinType.LEFT).join("category", JoinType.LEFT).get("id"),
					Integer.parseInt(category)));
		}
		if ((subCategory != null) && (!subCategory.isEmpty()) && (subCategory.matches("^[0-9]+$"))) {
			predicates.add(criteriaBuilder.equal(root.join("subCategory", JoinType.LEFT).get("id"),
					Integer.parseInt(subCategory)));
		}
		if ((name != null) && (!name.isEmpty())) {
			predicates
					.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("name")), "%" + name.toUpperCase() + "%"));
		}
		if ((brand != null) && (!brand.isEmpty())) {
			predicates.add(
					criteriaBuilder.like(criteriaBuilder.upper(root.get("brand")), "%" + brand.toUpperCase() + "%"));
		}
		if ((country != null) && (!country.isEmpty())) {
			predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("country")),
					"%" + country.toUpperCase() + "%"));
		}
		if ((consist != null) && (!consist.isEmpty())) {
			predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("consist")),
					"%" + consist.toUpperCase() + "%"));
		}
		if ((price != null) && (!price.isEmpty()) && (price.matches("^[0-9]+$"))) {
			predicates.add(criteriaBuilder.lt(root.get("price"), Integer.parseInt(price)));
		}
		criteriaQuery.select(criteriaBuilder.count(root)).where(predicates.toArray(new Predicate[] {}));
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}

}
