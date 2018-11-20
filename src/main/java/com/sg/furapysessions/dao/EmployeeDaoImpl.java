/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.furapysessions.dao;

import com.sg.furapysessions.model.Employee;
import com.sg.furapysessions.model.Role;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Thomas Graves
 */
public class EmployeeDaoImpl implements EmployeeDao {

    private static final String SQL_INSERT_EMPLOYEE
            = "INSERT INTO Employees (firstName, lastName, title, partner, "
            + "interests, employeeOfWeekCount, hireDate, roleID, userName, "
            + "password, pictureFile, isEmployeeOfTheWeek) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_DELETE_EMPLOYEE_BY_ID
            = "DELETE FROM Employees WHERE employeeID = ?";

    private static final String SQL_UPDATE_EMPLOYEE
            = "UPDATE Employees SET firstName = ?, lastName = ?, title = ?, partner = ?, "
            + "interests = ?, employeeOfWeekCount = ?, hireDate = ?, roleID = ?, password = ?"
            + "WHERE employeeID = ?";

    private static final String SQL_SELECT_EMPLOYEE_BY_ID
            = "SELECT * FROM Employees WHERE employeeID = ?";

    private static final String SQL_SELECT_ALL_EMPLOYEES
            = "SELECT * FROM Employees";

    private static final String SQL_SELECT_EMPLOYEES_BY_NAME
            = "SELECT * FROM Employees "
            + "WHERE CONCAT(firstName, ' ', lastName) LIKE ?";

    private static final String SQL_SELECT_EMPLOYEES_BY_ROLE_ID
            = "SELECT * FROM Employees "
            + "WHERE roleID = ?";

    private static final String SQL_SELECT_ROLE_BY_EMPLOYEE_ID
            = "SELECT * FROM Roles r "
            + "JOIN Employees e ON r.roleID = e.roleID "
            + "WHERE e.employeeID = ?";

    private static final String SQL_SELECT_EMPLOYEE_OF_THE_WEEK
            = "SELECT * FROM Employees where isEmployeeOfTheWeek";
    private static final String SQL_SET_EMPLOYEE_OF_THE_WEEK
            = "UPDATE Employees SET isEmployeeOfTheWeek = ? WHERE employeeID = ?";
    
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addEmployee(Employee employee) {
        jdbcTemplate.update(SQL_INSERT_EMPLOYEE,
                employee.getFirstName(),
                employee.getLastName(),
                employee.getTitle(),
                employee.getPartner(),
                employee.getInterests(),
                employee.getEmployeeOfWeekCount(),
                employee.getHireDate().toString(),
                employee.getRole().getRoleId(),
                employee.getUserName(),
                employee.getPassword(),
                employee.getPictureFile(),
                employee.isEOTW());
        employee.setEmployeeId(getLastInsertId());
    }

    @Override
    public void deleteEmployeeById(int employeeId) {
        jdbcTemplate.update(SQL_DELETE_EMPLOYEE_BY_ID, employeeId);
    }

    @Override
    public void updateEmployee(Employee employee) {
        jdbcTemplate.update(SQL_UPDATE_EMPLOYEE,
                employee.getFirstName(),
                employee.getLastName(),
                employee.getTitle(),
                employee.getPartner(),
                employee.getInterests(),
                employee.getEmployeeOfWeekCount(),
                employee.getHireDate().toString(),
                employee.getRole().getRoleId(),
                employee.getPassword(),
                employee.getEmployeeId());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Employee getEmployeeById(int employeeId) {
        try {
            Employee employee = jdbcTemplate.queryForObject(SQL_SELECT_EMPLOYEE_BY_ID,
                    new Mappers.EmployeeMapper(),
                    employeeId);
            employee.setRole(getRoleForEmployee(employee));
            return employee;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<Employee> getAllEmployees() {
        try {
            List<Employee> employeeList = jdbcTemplate.query(SQL_SELECT_ALL_EMPLOYEES, new Mappers.EmployeeMapper());
            return associateRolesWithEmployees(employeeList);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<Employee> getEmployeesByName(String name) {
        try {
            List<Employee> employeeList = jdbcTemplate.query(SQL_SELECT_EMPLOYEES_BY_NAME,
                    new Mappers.EmployeeMapper(),
                    // add wildcards to string input so SQL knows what to search for
                    "%" + name + "%");
            return associateRolesWithEmployees(employeeList);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<Employee> getEmployeesByRoleId(int roleId) {
        try {
            List<Employee> employeeList = jdbcTemplate.query(SQL_SELECT_EMPLOYEES_BY_ROLE_ID,
                    new Mappers.EmployeeMapper(),
                    roleId);
            return associateRolesWithEmployees(employeeList);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    
    @Override
    public Employee getEmployeeOfTheWeek() {
        try { 
            return jdbcTemplate.queryForObject(SQL_SELECT_EMPLOYEE_OF_THE_WEEK,
                    new Mappers.EmployeeMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    
    @Override
    public void setEmployeeOfTheWeek(Employee employee){
        jdbcTemplate.update(SQL_SET_EMPLOYEE_OF_THE_WEEK,
                employee.isEOTW(),
                employee.getEmployeeId());
    }

    // HELPER METHODS
    // ==============
    public int getLastInsertId() {
        return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
    }

    private Role getRoleForEmployee(Employee employee) {
        try {
            Role role = jdbcTemplate.queryForObject(SQL_SELECT_ROLE_BY_EMPLOYEE_ID,
                    new Mappers.RoleMapper(),
                    employee.getEmployeeId());
            return role;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private List<Employee> associateRolesWithEmployees(List<Employee> employeeList) {
        for (Employee currentEmployee : employeeList) {
            currentEmployee.setRole(getRoleForEmployee(currentEmployee));
        }
        return employeeList;
    }
    
    
}
