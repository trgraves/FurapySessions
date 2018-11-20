/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.furapysessions.dao;

import com.sg.furapysessions.model.Employee;
import java.util.List;

/**
 *
 * @author Thomas Graves
 */
public interface EmployeeDao {

    public void addEmployee(Employee employee);

    public void deleteEmployeeById(int employeeId);

    public void updateEmployee(Employee employee);

    public Employee getEmployeeById(int employeeId);

    public List<Employee> getAllEmployees();
    
    public List<Employee> getEmployeesByName(String name);
    
    public List<Employee> getEmployeesByRoleId(int roleId);
    
    public void setEmployeeOfTheWeek(Employee employee);
    
    public Employee getEmployeeOfTheWeek();
    
}
