package com.sombra.project.dao;

import java.util.List;

import com.sombra.project.entity.SubCategory;

public interface SubCategoryDao extends GenericDao<SubCategory> {

	List<SubCategory> findAllWithCategory();

}
