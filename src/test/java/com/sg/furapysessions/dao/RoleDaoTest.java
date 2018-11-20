/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.furapysessions.dao;

import com.sg.furapysessions.model.Role;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author patty
 */
public class RoleDaoTest {

    RoleDao roleDao;

    public RoleDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("test-applicationcontext.xml");
        roleDao = ctx.getBean("roleDao", RoleDao.class);

        // delete all Roles
        List<Role> roles = roleDao.getAllRoles();
        for (Role currentRole : roles) {
            roleDao.deleteRoleById(currentRole.getRoleId());
        }
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addRole method, of class RoleDao.
     */
    @Test
    public void testAddRole() {
        
        Role role = new Role();
        role.setRoleType("HR");
        roleDao.addRole(role);
        
        Role fromDao = roleDao.getRoleById(role.getRoleId());
        
        assertEquals(role, fromDao);
    }

    /**
     * Test of deleteRoleById method, of class RoleDao.
     */
    @Test
    public void testDeleteRoleById() {
        
        Role role = new Role();
        role.setRoleType("Event Committee");
        roleDao.addRole(role);
        
        roleDao.deleteRoleById(role.getRoleId());
        
        Role fromDao = roleDao.getRoleById(role.getRoleId());
        
        assertNull(fromDao);
              
    }

    /**
     * Test of updateRole method, of class RoleDao.
     */
    @Test
    public void testUpdateRole() {
        
        Role role = new Role();
        role.setRoleType("Employee");
        roleDao.addRole(role);
        
        role.setRoleType("Owner");
        roleDao.updateRole(role);
        
        Role fromDao = roleDao.getRoleById(role.getRoleId());
        
        assertEquals(role,fromDao);
    }

    /**
     * Test of getRoleById method, of class RoleDao.
     */
    @Test
    public void testGetRoleById() {
        
        Role role = new Role();
        role.setRoleType("HR");
        roleDao.addRole(role);
        
        Role fromDao = roleDao.getRoleById(role.getRoleId());
        
        Assert.assertEquals(role, fromDao);
    }

    /**
     * Test of getRoleById method, of class RoleDao.
     */
    @Test
    public void testGetAllRoles(){
        
        Role role = new Role();
        role.setRoleType("HR");
        roleDao.addRole(role);
        
        List<Role> roles = roleDao.getAllRoles();
        
        assertEquals(roles.size(), 1);
    }
    

}
