/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.furapysessions.controller;

import com.sg.furapysessions.dao.EmployeeDao;
import com.sg.furapysessions.dao.RoleDao;
import com.sg.furapysessions.model.Employee;
import com.sg.furapysessions.model.Role;
import java.io.File;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Ryan
 */
@Controller
public class EmployeeController {

    private RoleDao roleDao;
    private EmployeeDao employeeDao;
    public static final String PICTURE_FOLDER = "/images/";

    @Inject
    EmployeeController(RoleDao roleDao, EmployeeDao employeeDao) {
        this.roleDao = roleDao;
        this.employeeDao = employeeDao;
    }

    //Search Controller
    @RequestMapping(value = "/displayEmployees", method = RequestMethod.GET)
    public String displayEmployees(Model model) {
        List<Employee> employees = employeeDao.getAllEmployees();
        List<Role> roles = roleDao.getAllRoles();
        model.addAttribute("roles", roles);
        model.addAttribute("employees", employees);        
        return "employees";
    }

    @RequestMapping(value = "/searchEmployeeByName", method = RequestMethod.GET)
    public String searchEmployeeByName(HttpServletRequest request, Model model) {
        String name = request.getParameter("name");
        String role = request.getParameter("role");
        List<Employee> employees = employeeDao.getEmployeesByName(name);
        List<Role> roles = roleDao.getAllRoles();
        model.addAttribute("roles", roles);
        model.addAttribute("employees", employees);
        return "employees";
    }
    
    @RequestMapping(value="/searchEmployeeByRole", method = RequestMethod.GET)
    public String searchEmployeeByRole(HttpServletRequest request, Model model){
        String id = request.getParameter("roles");
        int roleId = parseInt(id);
        List<Employee> employees = employeeDao.getEmployeesByRoleId(roleId);
        List<Role> roles = roleDao.getAllRoles();
        model.addAttribute("roles", roles);
        model.addAttribute("employees", employees);
        return "employees";
    }

    @RequestMapping(value="/addEmployee", method = RequestMethod.POST)
    public String addEmployee(HttpServletRequest request,
            @RequestParam("picture") MultipartFile pictureFile){
        
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String title = request.getParameter("title");
        String partner = request.getParameter("partner");
        String interests = request.getParameter("interests");
        String date = request.getParameter("hireDate");
        String roleId = request.getParameter("employeeRole");
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");

        String filename = pictureFile.getOriginalFilename();
        
        try {
            
            String savePath = request.getSession().getServletContext()
                    .getRealPath("/") + PICTURE_FOLDER;
            File dir = new File(savePath);
            
            if (!dir.exists()){
                dir.mkdir();
            }
            
            pictureFile.transferTo(new File(savePath + "/" + filename));
        } catch (Exception e){
            System.err.println("Could not upload file. : "
            + e.toString());
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate hireDate = LocalDate.parse(date, formatter);
        Role role = roleDao.getRoleById(parseInt(roleId));

        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setTitle(title);
        employee.setPartner(partner);
        employee.setInterests(interests);
        employee.setHireDate(hireDate);
        employee.setRole(role);
        employee.setEmployeeOfWeekCount(0);
        employee.setUserName(userName);
        employee.setPassword(password);
        employee.setPictureFile(filename);
        employee.setEOTW(false);
        employeeDao.addEmployee(employee);
        return "redirect:manageEmployees";
    }

    @RequestMapping(value="/updateEmployee", method = RequestMethod.POST)
    public String updateEmployee(HttpServletRequest request){        
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String title = request.getParameter("title");
        String partner = request.getParameter("partner");
        String interests = request.getParameter("interests");      
        String roleId = request.getParameter("employeeRole");
        Role role = roleDao.getRoleById(parseInt(roleId));

        Employee employee = employeeDao.getEmployeeById(Integer.parseInt(request.getParameter("employeeId")));
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setTitle(title);
        employee.setPartner(partner);
        employee.setInterests(interests);
        employee.setRole(role);
        employeeDao.updateEmployee(employee);
        return "redirect:manageEmployees";
    }
    
    
    @RequestMapping(value = "/deleteEmployee", method = RequestMethod.GET)
    public String deleteEmployee(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("employeeId"));
        employeeDao.deleteEmployeeById(id);        
        return "redirect:manageEmployees";
    }

    //Role Controller
    @RequestMapping(value = "/addRole", method = RequestMethod.POST)
    public String addRole(HttpServletRequest request) {
        String roleType = request.getParameter("roleType");
        Role newRole = new Role();
        newRole.setRoleType(roleType);
        roleDao.addRole(newRole);
        return "redirect:manageEmployees";
    }

    @RequestMapping(value = "/getRole", method = RequestMethod.GET)
    public String getRole(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("roleId"));
        Role role = roleDao.getRoleById(id);
        model.addAttribute("role", role);
        model.addAttribute("roles", roleDao.getAllRoles());
        model.addAttribute("employees", employeeDao.getAllEmployees());
        return "CRUDEmployees";
    }

    @RequestMapping(value = "/updateRole", method = RequestMethod.POST)
    public String updateRole(HttpServletRequest request) {
        String input = request.getParameter("roleId");
        Role roleToEdit = new Role();
        if (input.length() != 0) {
            roleToEdit = roleDao.getRoleById(parseInt(input));
        }
        String roleType = request.getParameter("roleType");

        if (roleType.length() != 0) {
            roleToEdit.setRoleType(roleType);
            roleDao.updateRole(roleToEdit);
        }
        return "redirect:manageEmployees";
    }

    @RequestMapping(value = "/deleteRole", method = RequestMethod.GET)
    public String deleteRole(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("roleId"));
        roleDao.deleteRoleById(id);
        return "redirect:manageEmployees";
    }

    @RequestMapping(value = "/manageEmployees", method = RequestMethod.GET)
    public String displayManageEmployees(HttpServletRequest request, Model model) {
        List<Role> allRoles = roleDao.getAllRoles();
        List<Employee> allEmployees = employeeDao.getAllEmployees();
        model.addAttribute("roles", allRoles);
        model.addAttribute("employees", allEmployees);
        return "manageEmployees";
    }

}
