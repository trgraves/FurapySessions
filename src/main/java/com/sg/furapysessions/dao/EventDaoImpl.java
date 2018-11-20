/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.furapysessions.dao;

import com.sg.furapysessions.model.Category;
import com.sg.furapysessions.model.Event;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Ryan
 */
public class EventDaoImpl implements EventDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // =========================================================================
    // ===================== PREPARED STATEMENTS ===============================
    // =========================================================================
    private static final String SQL_INSERT_EVENT
            = "INSERT INTO Events (title, start, end, description, "
            + "location, isPrivate) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SQL_DELETE_EVENT_BY_ID
            = "DELETE FROM Events WHERE eventID = ?";

    private static final String SQL_DELETE_EVENTS_CATEGORIES
            = "DELETE FROM EventsCategories WHERE eventID = ?";

    private static final String SQL_UPDATE_EVENT
            = "UPDATE Events SET title = ?, start = ?,  end = ?, "
            + "description = ?, location = ?, isPrivate = ? "
            + "WHERE eventID = ?";

    private static final String SQL_SELECT_EVENT_BY_ID
            = "SELECT * FROM Events WHERE eventID = ?";

    private static final String SQL_SELECT_ALL_EVENTS
            = "SELECT * FROM Events";

    private static final String SQL_SELECT_EVENTS_BY_NAME
            = "SELECT * FROM Events WHERE title LIKE ?";

    private static final String SQL_SELECT_EVENTS_BY_DATE
            = "SELECT * FROM Events WHERE start LIKE ?";

    private static final String SQL_SELECT_EVENTS_BY_CATEGORY_NAME
            = "SELECT * FROM Events AS e "
            + "INNER JOIN EventsCategories AS ec "
            + "ON e.eventID = ec.eventID "
            + "INNER JOIN Categories AS c "
            + "ON ec.categoryID = c.categoryID "
            + "WHERE c.name LIKE ? ";

    private static final String SQL_SELECT_PUBLIC_EVENTS
            = "SELECT * FROM Events "
            + "WHERE isPrivate = false";

    //Prepared Statements for Helper Functions =================================
    private static final String SQL_INSERT_EVENTS_CATEGORIES
            = "INSERT INTO EventsCategories (eventID, categoryID) "
            + "VALUES (?, ?)";

    private static final String SQL_SELECT_CATEGORIES_BY_EVENT_ID
            = "SELECT * FROM Categories AS c "
            + "INNER JOIN EventsCategories AS ec "
            + "ON c.categoryID = ec.categoryID "
            + "WHERE ec.eventID = ?";

    // =========================================================================
    // ========================== EVENT CRUD METHODS ===========================
    // =========================================================================
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addEvent(Event event) {
        jdbcTemplate.update(SQL_INSERT_EVENT,
                event.getTitle(),
                event.getStart(),
                event.getEnd(),
                event.getEventDescription(),
                event.getLocation(),
                event.isPrivate());
        event.setEventId(jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class));
        // update events-categories table
        insertEventsCategories(event);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteEventById(int eventId) {

        // delete event-category relationship for event 
        jdbcTemplate.update(SQL_DELETE_EVENTS_CATEGORIES, eventId);

        // delete event
        jdbcTemplate.update(SQL_DELETE_EVENT_BY_ID, eventId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateEvent(Event event) {

        jdbcTemplate.update(SQL_UPDATE_EVENT,
                event.getTitle(),
                event.getStart(),
                event.getEnd(),
                event.getEventDescription(),
                event.getLocation(),
                event.isPrivate(),
                event.getEventId());

        // DELETE THEN RESET RELATIONSHIPS
        // events-categories relationship
        jdbcTemplate.update(SQL_DELETE_EVENTS_CATEGORIES, event.getEventId());
        insertEventsCategories(event);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Event getEventById(int eventId) {
        try {
            Event event
                    = jdbcTemplate.queryForObject(SQL_SELECT_EVENT_BY_ID,
                            new Mappers.EventMapper(), eventId);
            // get categories and set list
            event.setCategories(findCategoriesForEvents(event));

            return event;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<Event> getAllEvents() {
        try {
            List<Event> eventList = jdbcTemplate.query(SQL_SELECT_ALL_EVENTS,
                    new Mappers.EventMapper());
            // get categories and set list
            associateCategoryWithEvent(eventList);
            return eventList;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<Event> searchEventsByTitle(String title) {
        try {
            List<Event> eventList = jdbcTemplate.query(SQL_SELECT_EVENTS_BY_NAME,
                    new Mappers.EventMapper(),
                    "%" + title + "%");
            // get categories and set list
            return associateCategoryWithEvent(eventList);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<Event> searchEventsByDate(String eventDate) {
        try {
            List<Event> eventList = jdbcTemplate.query(SQL_SELECT_EVENTS_BY_DATE,
                    new Mappers.EventMapper(), "%" + eventDate + "%");
            // get categories and set list
            return associateCategoryWithEvent(eventList);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<Event> searchEventsByCategory(String categoryName) {
        try {
            List<Event> eventList = jdbcTemplate.query(SQL_SELECT_EVENTS_BY_CATEGORY_NAME,
                    new Mappers.EventMapper(), "%" + categoryName + "%");
            // get categories and set list
            return associateCategoryWithEvent(eventList);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<Event> getPublicEvents() {
        try {
            List<Event> eventList = jdbcTemplate.query(SQL_SELECT_PUBLIC_EVENTS,
                    new Mappers.EventMapper());
            // get categories and set list
            return associateCategoryWithEvent(eventList);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    // ========================================================================= 
    // Helpers for Events-Categories Relationships
    // =========================================================================
    private void insertEventsCategories(Event event) {
        final int eventId = event.getEventId();
        final List<Category> categories = event.getCategories();

        for (Category currentCategory : categories) {
            jdbcTemplate.update(SQL_INSERT_EVENTS_CATEGORIES,
                    eventId, currentCategory.getCategoryId());
        }
    }

    private List<Category> findCategoriesForEvents(Event event) {
        return jdbcTemplate.query(SQL_SELECT_CATEGORIES_BY_EVENT_ID,
                new Mappers.CategoryMapper(), event.getEventId());
    }

    private List<Event> associateCategoryWithEvent(List<Event> eventList) {
        for (Event currentEvent : eventList) {
            currentEvent.setCategories(findCategoriesForEvents(currentEvent));
        }
        return eventList;
    }

}
