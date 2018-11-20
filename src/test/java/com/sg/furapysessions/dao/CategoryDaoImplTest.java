/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.furapysessions.dao;

import java.util.List;
import com.sg.furapysessions.model.Category;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author tylerbates
 */
public class CategoryDaoImplTest {

    public CategoryDao catDao;
    public EventDao eventDao;

    public CategoryDaoImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("test-applicationContext.xml");
        catDao = ctx.getBean("categoryDao", CategoryDao.class);
        eventDao = ctx.getBean("eventDao", EventDao.class);

//        List<Event> events = eventDao.getAllEvents();
//        events.forEach((current) -> {
//            eventDao.deleteEventById(current.getEventId());
//        });
        List<Category> categories = catDao.getAllCategories();
        categories.forEach((current) -> {
            catDao.deleteCategoryById(current.getCategoryId());
        });
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getCategoryById method, of class CategoryDaoImpl.
     */
    @Test
    public void testAddGetCategoryById() {
        Category newCat = new Category();
        newCat.setName("Team Building");

        catDao.addCategory(newCat);

        assertEquals(newCat.getName(), 
                catDao.getCategoryById(newCat.getCategoryId())
                .getName());
    }

    /**
     * Test of getAllCategories method, of class CategoryDaoImpl.
     */
    @Test
    public void testGetAllCategories() {
        Category newCat = new Category();
        newCat.setName("Trust Building");
        catDao.addCategory(newCat);

        Category newCat2 = new Category();
        newCat2.setName("Time Management");
        catDao.addCategory(newCat2);
        
        Category newCat3 = new Category();
        newCat3.setName("Party");
        catDao.addCategory(newCat3);
        
        List<Category> cats = catDao.getAllCategories();
        
        assertEquals(3, cats.size());
        assertTrue(newCat.equals(cats.get(0)));
    }

    /**
     * Test of updateCategory method, of class CategoryDaoImpl.
     */
    @Test
    public void testUpdateCategory() {
        Category newCat = new Category();
        newCat.setCategoryId(1);
        newCat.setName("Trust Building");
        catDao.addCategory(newCat);

        Category newCat2 = new Category();
        newCat2.setCategoryId(1);
        newCat2.setName("Time Management");
        catDao.updateCategory(newCat2);
        
        assertNotSame(newCat.getName(),
                catDao.getCategoryById(newCat.getCategoryId())
                .getName());
    }

    /**
     * Test of deleteCategoryById method, of class CategoryDaoImpl.
     */
    @Test
    public void testDeleteCategoryById() {
        Category newCat = new Category();
        newCat.setCategoryId(1);
        newCat.setName("Trust Building");
        catDao.addCategory(newCat);

        Category newCat2 = new Category();
        newCat2.setCategoryId(2);
        newCat2.setName("Time Management");
        catDao.addCategory(newCat2);
        
        Category newCat3 = new Category();
        newCat3.setCategoryId(3);
        newCat3.setName("Party");
        catDao.addCategory(newCat3);
        
        assertEquals(3, catDao.getAllCategories().size());
        catDao.deleteCategoryById(1);
        
        assertEquals(2, catDao.getAllCategories().size());
        catDao.deleteCategoryById(2);
        
        assertEquals(1, catDao.getAllCategories().size());
    }

}
