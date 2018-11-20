/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.furapysessions.dao;

import com.sg.furapysessions.dao.Mappers.RoleMapper;
import com.sg.furapysessions.model.Role;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Ryan
 */
public class RoleDaoImpl implements RoleDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SQL_SELECT_LAST_INSERT_ID
            = "select LAST_INSERT_ID()";

    private static final String SQL_INSERT_ROLE
            = "INSERT INTO Roles (RoleType) VALUES (?)";

    private static final String SQL_DELETE_ROLE_BY_ID
            = "DELETE FROM Roles WHERE RoleID = ?";

    private static final String SQL_UPDATE_ROLE
            = "UPDATE Roles SET RoleType = ? WHERE RoleID = ?";

    private static final String SQL_SELECT_ROLE_BY_ID
            = "SELECT * FROM Roles WHERE RoleID = ?";

    private static final String SQL_SELECT_ALL_ROLES
            = "SELECT * FROM Roles";

    private static final String SQL_UPDATE_EMPLOYEES_SET_ROLE_ID_TO_NULL_BY_ROLE_ID
                = "UPDATE Employees SET roleID = NULL "
                + "WHERE roleID = ?";
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addRole(Role role) {
        jdbcTemplate.update(SQL_INSERT_ROLE,
                role.getRoleType());
        int roleId = jdbcTemplate.queryForObject(SQL_SELECT_LAST_INSERT_ID, Integer.class);
        role.setRoleId(roleId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteRoleById(int roleId) {
        // change roleID to null in Employees table
        jdbcTemplate.update(SQL_UPDATE_EMPLOYEES_SET_ROLE_ID_TO_NULL_BY_ROLE_ID, roleId);
        // delete role
        jdbcTemplate.update(SQL_DELETE_ROLE_BY_ID, roleId);
    }

    @Override
    public void updateRole(Role role) {
        jdbcTemplate.update(SQL_UPDATE_ROLE,
                role.getRoleType(),
                role.getRoleId());
    }

    @Override
    public Role getRoleById(int roleId) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_ROLE_BY_ID,
                    new RoleMapper(), roleId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }
    
    @Override
    public List<Role> getAllRoles() {
        try {
            return jdbcTemplate.query(SQL_SELECT_ALL_ROLES,
                    new RoleMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
