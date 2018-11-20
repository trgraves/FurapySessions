/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.furapysessions.controller;

import com.sg.furapysessions.dao.CategoryDao;
import com.sg.furapysessions.dao.EventDao;
import com.sg.furapysessions.model.Category;
import com.sg.furapysessions.model.Event;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Tyler Bates
 */
@Controller
public class EventController {

    private CategoryDao catDao;
    private EventDao eventDao;

    @Inject
    public EventController(CategoryDao catDao, EventDao eventDao) {
        this.catDao = catDao;
        this.eventDao = eventDao;
    }

    //--------------------CATEGORY------------------------
    @RequestMapping(value = "/addCategory", method = RequestMethod.POST)
    public String addCategory(HttpServletRequest request) {
        String name = request.getParameter("name");
        Category newCategory = new Category();
        newCategory.setName(name);
        catDao.addCategory(newCategory);
        return "redirect:manageEvents";
    }

    @RequestMapping(value = "/getCategory", method = RequestMethod.GET)
    public String getCategory(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("categoryId"));
        Category category = catDao.getCategoryById(id);
        model.addAttribute("category", category);
        model.addAttribute("categories", catDao.getAllCategories());
        return "CRUDEvents";
    }

    @RequestMapping(value = "/updateCategory", method = RequestMethod.POST)
    public String updateCategory(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("categoryId"));
        Category catToEdit = catDao.getCategoryById(id);
        catToEdit.setName(request.getParameter("name"));
        catDao.updateCategory(catToEdit);
        return "redirect:manageEvents";
    }

    @RequestMapping(value = "/deleteCategory", method = RequestMethod.GET)
    public String deleteCategory(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("categoryId"));
        catDao.deleteCategoryById(id);
        return "redirect:manageEvents";
    }

    @RequestMapping(value = "/manageEvents", method = RequestMethod.GET)
    public String displayManageEvents(HttpServletRequest request, Model model) {
        List<Event> allEvents = eventDao.getAllEvents();
        model.addAttribute("allEvents", allEvents);
        List<Category> allCategories = catDao.getAllCategories();
        model.addAttribute("categories", allCategories);
        return "manageEvents";
    }

    //--------------------Event------------------------
    @RequestMapping(value = "/addEvent", method = RequestMethod.POST)
    public String addEvent(HttpServletRequest request) {
        Event event = new Event();
        event.setTitle(request.getParameter("title"));
        // create date and format to yyyy-MM-dd pattern
        String date = request.getParameter("start");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(date, formatter);
        // call toString to set as string
        event.setStart(start.toString());
        // do the same for end date
        date = request.getParameter("end");
        LocalDate end = LocalDate.parse(date, formatter);
        event.setEnd(end.toString());
        event.setEventDescription(request.getParameter("description"));
        event.setLocation(request.getParameter("location"));
        boolean isPrivate = Boolean.parseBoolean(request.getParameter("isPrivate"));
        event.setPrivate(isPrivate);

        String[] categoriesPicked = request.getParameterValues("categories");
        List<Category> picked = new ArrayList<>();
        for (String current : categoriesPicked) {
            picked.add(catDao.getCategoryById(Integer.parseInt(current)));
        }

        event.setCategories(picked);
        eventDao.addEvent(event);
        return "redirect:manageEvents";
    }

    @RequestMapping(value = "/getEvent", method = RequestMethod.GET)
    public String getEvent(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("eventId"));
        Event event = eventDao.getEventById(id);
        model.addAttribute("eventToEdit", event);
        return "CRUDEvents";
    }

    @RequestMapping(value = "/updateEvent", method = RequestMethod.POST)
    public String updateEvent(HttpServletRequest request) {
        String id = request.getParameter("eventId");
        Event event = eventDao.getEventById(Integer.parseInt(id));
        event.setTitle(request.getParameter("title"));
        // create date and format to yyyy-MM-dd pattern
        String date = request.getParameter("start");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(date, formatter);
        // call toString to set as string
        event.setStart(start.toString());
        // do the same for end date
        date = request.getParameter("end");
        LocalDate end = LocalDate.parse(date, formatter);
        event.setEnd(end.toString());
        event.setEventDescription(request.getParameter("description"));
        event.setLocation(request.getParameter("location"));
        boolean isPrivate = Boolean.parseBoolean(request.getParameter("isPrivate"));
        event.setPrivate(isPrivate);

        String[] categoriesPicked = request.getParameterValues("categories");
        List<Category> picked = new ArrayList<>();
        for (String current : categoriesPicked) {
            picked.add(catDao.getCategoryById(Integer.parseInt(current)));
        }

        event.setCategories(picked);
        eventDao.updateEvent(event);
        return "redirect:manageEvents";
    }

    @RequestMapping(value = "/deleteEvent", method = RequestMethod.GET)
    public String deleteEvent(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("eventId"));
        eventDao.deleteEventById(id);
        return "redirect:manageEvents";
    }
    
    //--------------------Event------------------------
    //--------------------Search Event------------------------
    @RequestMapping(value = "/displayEvents", method = RequestMethod.GET)
    public String displayEvents(HttpServletRequest request, Model model) {
        List<Event> eventList = eventDao.getAllEvents();
        model.addAttribute("eventList", eventList);
        
        return "events";
    }
    //--------------------Search Event By Title------------------------
    @RequestMapping(value = "/searchTitle", method = RequestMethod.GET)
    public String searchTitle(HttpServletRequest request, Model model) {
        String searchTitle = request.getParameter("searchTitle");
        List<Event> eventList = eventDao.searchEventsByTitle(searchTitle);
        model.addAttribute("eventList", eventList);
        
        return "events";
    }
    //--------------------Search Event By Start Date------------------------
    @RequestMapping(value = "/searchStart", method = RequestMethod.GET)
    public String searchStart(HttpServletRequest request, Model model) {
        String searchStart = request.getParameter("searchStart");
        List<Event> eventList = eventDao.searchEventsByDate(searchStart);
        model.addAttribute("eventList", eventList);
        
        return "events";
    }
    //--------------------Search Event By Category------------------------
    @RequestMapping(value = "/searchCategory", method = RequestMethod.GET)
    public String searchCategory(HttpServletRequest request, Model model) {
        String searchCategory = request.getParameter("searchCategory");
        List<Event> eventList = eventDao.searchEventsByCategory(searchCategory);
        model.addAttribute("eventList", eventList);
        
        return "events";
    }

}
