package com.sg.furapysessions.dao;

import com.sg.furapysessions.model.Category;
import com.sg.furapysessions.model.Employee;
import com.sg.furapysessions.model.Event;
import com.sg.furapysessions.model.Role;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author tylerbates
 */
public class Mappers {

    public static final class CategoryMapper implements RowMapper<Category> {

        @Override
        public Category mapRow(ResultSet rs, int i) throws SQLException {
            Category category = new Category();
            category.setCategoryId(rs.getInt("categoryId"));
            category.setName(rs.getString("name"));
            return category;
        }

    }

    public static final class RoleMapper implements RowMapper<Role> {

        @Override
        public Role mapRow(ResultSet rs, int i) throws SQLException {
            Role role = new Role();
            role.setRoleType(rs.getString("RoleType"));
            role.setRoleId(rs.getInt("RoleID"));
            return role;
        }

    }

    public static final class EmployeeMapper implements RowMapper<Employee> {

        @Override
        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
            Employee employee = new Employee();
            employee.setEmployeeId(rs.getInt("employeeID"));
            employee.setFirstName(rs.getString("firstName"));
            employee.setLastName(rs.getString("lastName"));
            employee.setTitle(rs.getString("title"));
            employee.setPartner(rs.getString("partner"));
            employee.setInterests(rs.getString("interests"));
            employee.setEmployeeOfWeekCount(rs.getInt("employeeOfWeekCount"));
            employee.setHireDate(rs.getTimestamp("hireDate").toLocalDateTime().toLocalDate());
            employee.setUserName(rs.getString("userName"));
            employee.setPassword(rs.getString("password"));
            employee.setPictureFile(rs.getString("pictureFile"));
            // get role from helper function
            return employee;
        }

    }

    public static final class EventMapper implements RowMapper<Event> {

        @Override
        public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
            Event event = new Event();
            event.setEventId(rs.getInt("eventID"));
            event.setTitle(rs.getString("title"));
            event.setStart(rs.getString("start"));
            event.setEnd(rs.getString("end"));
            event.setEventDescription(rs.getString("description"));
            event.setLocation(rs.getString("location"));
            event.setPrivate(rs.getBoolean("isPrivate"));
            // get category with helper functions

            return event;
        }
    }
}
