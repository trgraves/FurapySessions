/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.furapysessions.dao;

import com.sg.furapysessions.model.Category;
import com.sg.furapysessions.model.Event;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Ryan
 */
public class EventDaoTest {

    CategoryDao catDao;
    EventDao eventDao;

    public EventDaoTest() {
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

        // delete all Events
        List<Event> events = eventDao.getAllEvents();
        for (Event currentEvent : events) {
            eventDao.deleteEventById(currentEvent.getEventId());
        }

        // delete all Categories
        List<Category> categories = catDao.getAllCategories();
        for (Category currentCategory : categories) {
            catDao.deleteCategoryById(currentCategory.getCategoryId());
        }
    }

    @After
    public void tearDown() {
        // delete all Events
        List<Event> events = eventDao.getAllEvents();
        for (Event currentEvent : events) {
            eventDao.deleteEventById(currentEvent.getEventId());
        }

        // delete all Categories
        List<Category> categories = catDao.getAllCategories();
        for (Category currentCategory : categories) {
            catDao.deleteCategoryById(currentCategory.getCategoryId());
        }
    }

    /**
     * Test of addEvent method, of class EventDao.
     */
    @Test
    public void testAddGetEvent() {

        //Category
        Category category = new Category();
        category.setName("Meeting");
        catDao.addCategory(category);

        //Event
        Event event = new Event();
        event.setTitle("Penguin Parade");
        event.setStart("2019-10-18");
        event.setEnd("2019-10-18");
        event.setEventDescription("It's too hot for a penguin.");
        event.setLocation("The IceBox");
        event.isPrivate();

        List<Category> categories = new ArrayList<>();
        categories.add(category);
        event.setCategories(categories);

        eventDao.addEvent(event);

        Event fromDao = eventDao.getEventById(event.getEventId());
        assertEquals(fromDao, event);

    }

    /**
     * Test of deleteEventById method, of class EventDao.
     */
    @Test
    public void testDeleteEventById() {

        //Category
        Category category = new Category();
        category.setName("Team Building");
        catDao.addCategory(category);

        //Event
        Event event = new Event();
        event.setTitle("TrustFalls");
        event.setStart("2019-10-10");
        event.setEnd("2019-10-10");
        event.setEventDescription("Do you trust your co-workers?");
        event.setLocation("The BreakRoom");
        event.isPrivate();

        List<Category> categories = new ArrayList<>();
        categories.add(category);
        event.setCategories(categories);

        eventDao.addEvent(event);
        eventDao.deleteEventById(event.getEventId());

        Event fromDao = eventDao.getEventById(event.getEventId());
        assertEquals(null, fromDao);
    }

    /**
     * Test of updateEvent method, of class EventDao.
     */
    @Test
    public void testUpdateEvent() {

        //Category
        Category category = new Category();
        category.setName("Team Building");
        catDao.addCategory(category);

        //Event
        Event event = new Event();
        event.setTitle("TrustFalls");
        event.setStart("2019-10-10");
        event.setEnd("2019-10-10");
        event.setEventDescription("Do you trust your co-workers?");
        event.setLocation("The BreakRoom");
        event.isPrivate();

        List<Category> categories = new ArrayList<>();
        categories.add(category);
        event.setCategories(categories);

        eventDao.addEvent(event);

        event.setEnd("2019-10-11");
        eventDao.updateEvent(event);

        Event fromDao = eventDao.getEventById(event.getEventId());
        assertEquals(event.getEnd(), fromDao.getEnd());
    }

    /**
     * Test of getAllEvents method, of class EventDao.
     */
    @Test
    public void testGetAllEvents() {
        // EVENT 1
        //Category
        Category category1 = new Category();
        category1.setName("Meeting");
        catDao.addCategory(category1);

        //Event
        Event event1 = new Event();
        event1.setTitle("Penguin Parade");
        event1.setStart("2019-10-18");
        event1.setEnd("2019-10-18");
        event1.setEventDescription("It's too hot for a penguin.");
        event1.setLocation("The IceBox");
        event1.isPrivate();

        List<Category> categories1 = new ArrayList<>();
        categories1.add(category1);
        event1.setCategories(categories1);

        eventDao.addEvent(event1);

        // EVENT 2
        //Category
        Category category2 = new Category();
        category2.setName("Team Building");
        catDao.addCategory(category2);

        //Event
        Event event2 = new Event();
        event2.setTitle("TrustFalls");
        event2.setStart("2019-10-10");
        event2.setEnd("2019-10-10");
        event2.setEventDescription("Do you trust your co-workers?");
        event2.setLocation("The BreakRoom");
        event2.isPrivate();

        List<Category> categories2 = new ArrayList<>();
        categories2.add(category2);
        event2.setCategories(categories2);

        eventDao.addEvent(event2);

        List<Event> fromDao = eventDao.getAllEvents();
        assertEquals(2, fromDao.size());

    }

    /**
     * Test of searchEventsByName method, of class EventDao.
     */
    @Test
    public void testSearchEventsByName() {
        // EVENT 1
        //Category
        Category category1 = new Category();
        category1.setName("Meeting");
        catDao.addCategory(category1);

        //Event
        Event event1 = new Event();
        event1.setTitle("Penguin Parade");
        event1.setStart("2019-10-18");
        event1.setEnd("2019-10-18");
        event1.setEventDescription("It's too hot for a penguin.");
        event1.setLocation("The IceBox");
        event1.isPrivate();

        List<Category> categories1 = new ArrayList<>();
        categories1.add(category1);
        event1.setCategories(categories1);

        eventDao.addEvent(event1);

        // EVENT 2
        //Category
        Category category2 = new Category();
        category2.setName("Team Building");
        catDao.addCategory(category2);

        //Event
        Event event2 = new Event();
        event2.setTitle("TrustFalls");
        event2.setStart("2019-10-10");
        event2.setEnd("2019-10-10");
        event2.setEventDescription("Do you trust your co-workers?");
        event2.setLocation("The BreakRoom");
        event2.isPrivate();

        List<Category> categories2 = new ArrayList<>();
        categories2.add(category2);
        event2.setCategories(categories2);

        eventDao.addEvent(event2);

        assertEquals(2, eventDao.getAllEvents().size());
        assertEquals(2, eventDao.searchEventsByTitle("A").size());
        assertEquals(1, eventDao.searchEventsByTitle("P").size());
        assertEquals(1, eventDao.searchEventsByTitle("Penguin").size());
        assertEquals(0, eventDao.searchEventsByTitle("z").size());

    }

    /**
     * Test of searchEventsByDate method, of class EventDao.
     */
    @Test
    public void testSearchEventsByDate() {
        // EVENT 1
        //Category
        Category category1 = new Category();
        category1.setName("Meeting");
        catDao.addCategory(category1);

        //Event
        Event event1 = new Event();
        event1.setTitle("Penguin Parade");
        event1.setStart("2019-10-18");
        event1.setEnd("2019-10-18");
        event1.setEventDescription("It's too hot for a penguin.");
        event1.setLocation("The IceBox");
        event1.isPrivate();

        List<Category> categories1 = new ArrayList<>();
        categories1.add(category1);
        event1.setCategories(categories1);

        eventDao.addEvent(event1);

        // EVENT 2
        //Category
        Category category2 = new Category();
        category2.setName("Team Building");
        catDao.addCategory(category2);

        //Event
        Event event2 = new Event();
        event2.setTitle("TrustFalls");
        event2.setStart("2019-10-10");
        event2.setEnd("2019-10-10");
        event2.setEventDescription("Do you trust your co-workers?");
        event2.setLocation("The BreakRoom");
        event2.isPrivate();

        List<Category> categories2 = new ArrayList<>();
        categories2.add(category2);
        event2.setCategories(categories2);

        eventDao.addEvent(event2);
        
        assertEquals(2, eventDao.getAllEvents().size());
        assertEquals(2, eventDao.searchEventsByDate("2019").size());
        assertEquals(0, eventDao.searchEventsByDate("2018").size());
        assertEquals(1, eventDao.searchEventsByDate("2019-10-10").size());
        

    }

    /**
     * Test of searchEventsByCategory method, of class EventDao.
     */
    @Test
    public void testSearchEventsByCategory() {
        // EVENT 1
        //Category
        Category category1 = new Category();
        category1.setName("Meeting");
        catDao.addCategory(category1);

        //Event
        Event event1 = new Event();
        event1.setTitle("Penguin Parade");
        event1.setStart("2019-10-18");
        event1.setEnd("2019-10-18");
        event1.setEventDescription("It's too hot for a penguin.");
        event1.setLocation("The IceBox");
        event1.isPrivate();

        List<Category> categories1 = new ArrayList<>();
        categories1.add(category1);
        event1.setCategories(categories1);

        eventDao.addEvent(event1);

        // EVENT 2
        //Category
        Category category2 = new Category();
        category2.setName("Team Building");
        catDao.addCategory(category2);

        //Event
        Event event2 = new Event();
        event2.setTitle("TrustFalls");
        event2.setStart("2019-10-10");
        event2.setEnd("2019-10-10");
        event2.setEventDescription("Do you trust your co-workers?");
        event2.setLocation("The BreakRoom");
        event2.isPrivate();

        List<Category> categories2 = new ArrayList<>();
        categories2.add(category2);
        event2.setCategories(categories2);

        eventDao.addEvent(event2);
        
        assertEquals(2, eventDao.getAllEvents().size());
        assertEquals(2, eventDao.searchEventsByCategory("e").size());
        assertEquals(1, eventDao.searchEventsByCategory("Team").size());
        assertEquals(2, eventDao.searchEventsByCategory("t").size());
        assertEquals(1, eventDao.searchEventsByCategory("Meeting").size());
    }
    
    @Test
    public void testGetPublicEvents() {        
        //Category
        Category category1 = new Category();
        category1.setName("Meeting");
        catDao.addCategory(category1);
        
        //Event
        Event event1 = new Event();
        event1.setTitle("Penguin Parade");
        event1.setStart("2019-10-18");
        event1.setEnd("2019-10-18");
        event1.setEventDescription("It's too hot for a penguin.");
        event1.setLocation("The IceBox");
        event1.setPrivate(true);
        
        List<Category> categories1 = new ArrayList<>();
        categories1.add(category1);
        event1.setCategories(categories1);
        
        eventDao.addEvent(event1);
        assertEquals(0, eventDao.getPublicEvents().size());
        
        //Event
        Event event2 = new Event();
        event2.setTitle("TrustFalls");
        event2.setStart("2019-10-10");
        event2.setEnd("2019-10-10");
        event2.setEventDescription("Do you trust your co-workers?");
        event2.setLocation("The BreakRoom");
        event2.setPrivate(false);      
        event2.setCategories(categories1);
        
        eventDao.addEvent(event2);
        assertEquals(1, eventDao.getPublicEvents().size());
        
        event1.setPrivate(false);
        eventDao.updateEvent(event1);
        assertEquals(2, eventDao.getPublicEvents().size());        
    }

}
