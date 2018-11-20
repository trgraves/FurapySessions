/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.furapysessions.controller;

import com.sg.furapysessions.dao.CategoryDao;
import com.sg.furapysessions.dao.EmployeeDao;
import com.sg.furapysessions.dao.EventDao;
import com.sg.furapysessions.dao.RoleDao;
import com.sg.furapysessions.model.Category;
import com.sg.furapysessions.model.Employee;
import com.sg.furapysessions.model.Event;
import com.sg.furapysessions.model.Role;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author tgraves
 */
@Controller
public class RestController {
    
    EmployeeDao empDao;
    RoleDao roleDao;
    EventDao eventDao;
    CategoryDao catDao;
    
    @Inject
    public RestController(EmployeeDao empDao, RoleDao roleDao, EventDao eventDao, CategoryDao catDao){
        this.empDao = empDao;
        this.roleDao = roleDao;
        this.eventDao = eventDao;
        this.catDao = catDao;
    }
    
    @ResponseBody
    @RequestMapping(value = "/editRole", method = RequestMethod.GET)
    public Role getRoleToEdit(HttpServletRequest request) {
        String id = request.getParameter("roleId");
        int roleId = Integer.parseInt(id);
        return roleDao.getRoleById(roleId);
    }
    
    @ResponseBody
    @RequestMapping(value = "/editEmployee", method = RequestMethod.GET)
    public Employee getEmployeeToEdit(HttpServletRequest request) {
        String id = request.getParameter("employeeId");
        int employeeId = Integer.parseInt(id);
        return empDao.getEmployeeById(employeeId);
    }
    
    @ResponseBody
    @RequestMapping(value = "/editCategory", method = RequestMethod.GET)
    public Category getCategoryToEdit(HttpServletRequest request) {
        String id = request.getParameter("categoryId");
        int categoryId = Integer.parseInt(id);
        return catDao.getCategoryById(categoryId);
    }
    
    @ResponseBody
    @RequestMapping(value = "/editEvent", method = RequestMethod.GET)
    public Event getEventToEdit(HttpServletRequest request) {
        String id = request.getParameter("eventId");
        int eventId = Integer.parseInt(id);
        return eventDao.getEventById(eventId);
    }
    
}
