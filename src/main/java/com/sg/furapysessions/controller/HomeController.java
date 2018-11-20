/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.furapysessions.controller;

import com.sg.furapysessions.dao.EmployeeDao;
import com.sg.furapysessions.dao.EventDao;
import com.sg.furapysessions.model.Employee;
import com.sg.furapysessions.model.Event;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Thomas Graves
 */
@Controller
public class HomeController {

    EmployeeDao empDao;
    EventDao eventDao;

    @Inject
    public HomeController(EmployeeDao empDao, EventDao eventDao) {
        this.empDao = empDao;
        this.eventDao = eventDao;
    }

    @RequestMapping(value = {"/", "index"}, method = RequestMethod.GET)
    public String displayIndex(HttpServletRequest request, Model model) {
        model.addAttribute("EOTW", empDao.getEmployeeOfTheWeek());
        model.addAttribute("employees", empDao.getAllEmployees());
        return "index";
    }

    @ResponseBody
    @RequestMapping(value = "/calendar/private", method = RequestMethod.GET)
    public List<Event> getCalendarPrivate(HttpServletRequest request) { 
        return eventDao.getAllEvents();
    }
    
    @ResponseBody
    @RequestMapping(value = "/calendar/public", method = RequestMethod.GET)
    public List<Event> getCalendarPublic(HttpServletRequest request) {        
        return eventDao.getPublicEvents();
    }

    @RequestMapping(value = "/addEotW", method = RequestMethod.POST)
    public String displayEmployeeOfTheWeek(HttpServletRequest request, Model model) {
        int employeeId = Integer.parseInt(request.getParameter("employeeId"));
        Employee newEOTW = empDao.getEmployeeById(employeeId);

        if (empDao.getEmployeeOfTheWeek() == null) {
            newEOTW.setEOTW(true);
            empDao.setEmployeeOfTheWeek(newEOTW);
            newEOTW.setEmployeeOfWeekCount(newEOTW.getEmployeeOfWeekCount() + 1);
        } else {
            Employee oldEOTW = empDao.getEmployeeOfTheWeek();
            oldEOTW.setEOTW(false);
            empDao.setEmployeeOfTheWeek(oldEOTW);
            newEOTW.setEOTW(true);
            empDao.setEmployeeOfTheWeek(newEOTW);
            newEOTW.setEmployeeOfWeekCount(newEOTW.getEmployeeOfWeekCount() + 1);
        }
        
        String description = request.getParameter("description");
        model.addAttribute("description", description);
        model.addAttribute("EOTW", empDao.getEmployeeOfTheWeek());
        model.addAttribute("employees", empDao.getAllEmployees());
        return "index";
    }
}
