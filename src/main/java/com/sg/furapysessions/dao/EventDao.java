/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.furapysessions.dao;

import com.sg.furapysessions.model.Category;
import com.sg.furapysessions.model.Event;
import java.util.List;

/**
 *
 * @author Ryan
 */
public interface EventDao {

    public void addEvent(Event event);
    
    public void deleteEventById(int eventId); 
    
    public void updateEvent(Event event);
    
    public Event getEventById(int eventId);
    
    public List<Event> getAllEvents();
    
    public List<Event> searchEventsByTitle(String title);
    
    public List<Event> searchEventsByDate(String eventDate);
    
    public List<Event> searchEventsByCategory(String categoryName);
    
    public List<Event> getPublicEvents();
}
