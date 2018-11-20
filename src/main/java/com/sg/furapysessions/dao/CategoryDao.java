/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.furapysessions.dao;

import com.sg.furapysessions.model.Category;
import java.util.List;

/**
 *
 * @author Tyler Bates
 */
public interface CategoryDao {
    
    public Category getCategoryById(int categoryId);
    public List<Category> getAllCategories();
    public void addCategory(Category category);
    public void updateCategory(Category category);
    public void deleteCategoryById(int categoryId);
    
}
