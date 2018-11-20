/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.furapysessions.dao;

import com.sg.furapysessions.model.Category;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Tyler Bates
 */
public class CategoryDaoImpl implements CategoryDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final class queries {

        private static final String SELECT_CATEGORY_BY_ID
                = "select * from categories where categoryid = ?";
        private static final String SELECT_ALL_CATEGORIES
                = "select * from categories";
        private static final String INSERT_CATEGORY
                = "insert into categories "
                + "(name) "
                + "values (?)";
        private static final String INSERT_CATEGORY_BY_ID
                = "insert into categories "
                + "(categoryid,name) "
                + "values (?,?)";
        private static final String UPDATE_CATEGORY
                = "update categories set "
                + "name = ? "
                + "where categoryId = ?";
        private static final String DELETE_CATEGORY
                = "delete from categories where categoryid = ?";
        private static final String DELETE_CATEGORIES_FOR_EVENTS
                = "delete from eventscategories where categoryid = ?";
    }

    @Override
    public Category getCategoryById(int categoryId) {
        try {
            return jdbcTemplate.queryForObject(
                    queries.SELECT_CATEGORY_BY_ID,
                    new Mappers.CategoryMapper(),
                    categoryId);
        } catch (EmptyResultDataAccessException e) {
            System.err.println("Could not pull category by ID.");
            return null;
        }
    }

    @Override
    public List<Category> getAllCategories() {
        try {
            return jdbcTemplate.query(
                    queries.SELECT_ALL_CATEGORIES,
                    new Mappers.CategoryMapper());
        } catch (EmptyResultDataAccessException e) {
            System.err.println("Could not get all categories.");
            return null;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addCategory(Category category) {
        if (category.getCategoryId() != 0) {
            jdbcTemplate.update(queries.INSERT_CATEGORY_BY_ID,
                    category.getCategoryId(),
                    category.getName());
        } else {
            jdbcTemplate.update(queries.INSERT_CATEGORY,
                    category.getName());
            category.setCategoryId(getLastInsertId());
        }
    }

    @Override
    public void updateCategory(Category category) {
        jdbcTemplate.update(queries.UPDATE_CATEGORY,
                category.getName(),
                category.getCategoryId());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteCategoryById(int categoryId) {
        jdbcTemplate.update(queries.DELETE_CATEGORIES_FOR_EVENTS, categoryId);
        jdbcTemplate.update(queries.DELETE_CATEGORY, categoryId);
    }

    private int getLastInsertId() {
        return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
    }
}
