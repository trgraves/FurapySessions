/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.furapysessions.dao;

import com.sg.furapysessions.model.Role;
import java.util.List;

/**
 *
 * @author patty
 */
public interface RoleDao {
    
    public void addRole(Role role);
    
    public void deleteRoleById(int roleId);
    
    public void updateRole(Role role);
    
    public Role getRoleById(int roleId);
    
    public List<Role> getAllRoles();

}
