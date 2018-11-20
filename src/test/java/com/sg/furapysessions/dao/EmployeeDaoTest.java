/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.furapysessions.dao;

import com.sg.furapysessions.model.Employee;
import com.sg.furapysessions.model.Role;
import java.time.LocalDate;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author tgraves
 */


public class EmployeeDaoTest {

    private EmployeeDao empDao;
    private RoleDao roleDao;

    public EmployeeDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("test-applicationContext.xml");
        empDao = ctx.getBean("employeeDao", EmployeeDao.class);
        roleDao = ctx.getBean("roleDao", RoleDao.class);

        List<Employee> employeeList = empDao.getAllEmployees();
        employeeList.forEach((current) -> {
            empDao.deleteEmployeeById(current.getEmployeeId());
        });

        List<Role> roleList = roleDao.getAllRoles();
        roleList.forEach((current) -> {
            roleDao.deleteRoleById(current.getRoleId());
        });
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAddGetEmployee() {
        Role role = new Role();
        role.setRoleType("HR");
        roleDao.addRole(role);

        Employee emp1 = new Employee();
        emp1.setFirstName("Tom");
        emp1.setLastName("Graves");
        emp1.setTitle("Dog Petter");
        emp1.setPartner("Chonkers");
        emp1.setInterests("Walking the dogs, no bamboozlin");
        emp1.setEmployeeOfWeekCount(5);
        emp1.setHireDate(LocalDate.parse("2018-10-25"));
        emp1.setRole(role);
        emp1.setUserName("tgraves");
        emp1.setPassword("woofwoof");
        empDao.addEmployee(emp1);

        Employee fromDao = empDao.getEmployeeById(emp1.getEmployeeId());
        assertEquals(emp1, fromDao);
    }

    @Test
    public void testDeleteEmployee() {
        Role role = new Role();
        role.setRoleType("HR");
        roleDao.addRole(role);

        Employee emp1 = new Employee();
        emp1.setFirstName("Tom");
        emp1.setLastName("Graves");
        emp1.setTitle("Dog Petter");
        emp1.setPartner("Chonkers");
        emp1.setInterests("Walking the dogs, no bamboozlin");
        emp1.setEmployeeOfWeekCount(5);
        emp1.setHireDate(LocalDate.parse("2018-10-25"));
        emp1.setRole(role);
        emp1.setUserName("tgraves");
        emp1.setPassword("woofwoof");
        empDao.addEmployee(emp1);

        Employee fromDao = empDao.getEmployeeById(emp1.getEmployeeId());
        assertEquals(emp1, fromDao);

        empDao.deleteEmployeeById(emp1.getEmployeeId());
        assertNull(empDao.getEmployeeById(emp1.getEmployeeId()));
    }

    @Test
    public void testUpdateEmployee() {
        Role role = new Role();
        role.setRoleType("HR");
        roleDao.addRole(role);

        Employee emp1 = new Employee();
        emp1.setFirstName("Tom");
        emp1.setLastName("Graves");
        emp1.setTitle("Dog Petter");
        emp1.setPartner("Chonkers");
        emp1.setInterests("Walking the dogs, no bamboozlin");
        emp1.setEmployeeOfWeekCount(5);
        emp1.setHireDate(LocalDate.parse("2018-10-25"));
        emp1.setRole(role);
        emp1.setUserName("tgraves");
        emp1.setPassword("woofwoof");
        empDao.addEmployee(emp1);

        Employee fromDao = empDao.getEmployeeById(emp1.getEmployeeId());
        assertEquals(emp1, fromDao);

        emp1.setFirstName("Tyler");
        emp1.setLastName("Bates");
        empDao.updateEmployee(emp1);
        assertNotEquals(emp1, fromDao);

        fromDao = empDao.getEmployeeById(emp1.getEmployeeId());
        assertEquals(emp1, fromDao);
    }

    @Test
    public void testGetAllEmployees() {
        Role role = new Role();
        role.setRoleType("HR");
        roleDao.addRole(role);

        Employee emp1 = new Employee();
        emp1.setFirstName("Tom");
        emp1.setLastName("Graves");
        emp1.setTitle("Dog Petter");
        emp1.setPartner("Chonkers");
        emp1.setInterests("Walking the dogs, no bamboozlin");
        emp1.setEmployeeOfWeekCount(5);
        emp1.setHireDate(LocalDate.parse("2018-10-25"));
        emp1.setRole(role);
        emp1.setUserName("tgraves");
        emp1.setPassword("woofwoof");
        empDao.addEmployee(emp1);

        Role role2 = new Role();
        role2.setRoleType("Event Committee");
        roleDao.addRole(role2);

        Employee emp2 = new Employee();
        emp2.setFirstName("Tyler");
        emp2.setLastName("Bates");
        emp2.setTitle("Dog Therapist");
        emp2.setPartner("Dixie");
        emp2.setInterests("Therapying the dogs, no bamboozlin");
        emp2.setEmployeeOfWeekCount(8);
        emp2.setHireDate(LocalDate.parse("2018-10-25"));
        emp2.setRole(role2);
        emp2.setUserName("tbates");
        emp2.setPassword("borkbork");
        empDao.addEmployee(emp2);

        assertEquals(2, empDao.getAllEmployees().size());
        empDao.deleteEmployeeById(emp1.getEmployeeId());
        assertNull(empDao.getEmployeeById(emp1.getEmployeeId()));
        assertEquals(1, empDao.getAllEmployees().size());
    }

    @Test
    public void testGetEmployeesByName() {
        Role role = new Role();
        role.setRoleType("HR");
        roleDao.addRole(role);

        Employee emp1 = new Employee();
        emp1.setFirstName("Tom");
        emp1.setLastName("Graves");
        emp1.setTitle("Dog Petter");
        emp1.setPartner("Chonkers");
        emp1.setInterests("Walking the dogs, no bamboozlin");
        emp1.setEmployeeOfWeekCount(5);
        emp1.setHireDate(LocalDate.parse("2018-10-25"));
        emp1.setRole(role);
        emp1.setUserName("tgraves");
        emp1.setPassword("woofwoof");
        empDao.addEmployee(emp1);

        Role role2 = new Role();
        role2.setRoleType("Event Committee");
        roleDao.addRole(role2);

        Employee emp2 = new Employee();
        emp2.setFirstName("Tyler");
        emp2.setLastName("Bates");
        emp2.setTitle("Dog Therapist");
        emp2.setPartner("Dixie");
        emp2.setInterests("Therapying the dogs, no bamboozlin");
        emp2.setEmployeeOfWeekCount(8);
        emp2.setHireDate(LocalDate.parse("2018-10-25"));
        emp2.setRole(role2);
        emp2.setUserName("tbates");
        emp2.setPassword("borkbork");
        empDao.addEmployee(emp2);

        assertEquals(2, empDao.getAllEmployees().size());
        assertEquals(2, empDao.getEmployeesByName("T").size());
        assertEquals(1, empDao.getEmployeesByName("to").size());
        assertEquals(1, empDao.getEmployeesByName("Bates").size());
    }

    @Test
    public void testGetEmployeesByRoleId() {
        Role role = new Role();
        role.setRoleType("HR");
        roleDao.addRole(role);

        Employee emp1 = new Employee();
        emp1.setFirstName("Tom");
        emp1.setLastName("Graves");
        emp1.setTitle("Dog Petter");
        emp1.setPartner("Chonkers");
        emp1.setInterests("Walking the dogs, no bamboozlin");
        emp1.setEmployeeOfWeekCount(5);
        emp1.setHireDate(LocalDate.parse("2018-10-25"));
        emp1.setRole(role);
        emp1.setUserName("tgraves");
        emp1.setPassword("woofwoof");
        empDao.addEmployee(emp1);

        Employee emp2 = new Employee();
        emp2.setFirstName("Tyler");
        emp2.setLastName("Bates");
        emp2.setTitle("Dog Therapist");
        emp2.setPartner("Dixie");
        emp2.setInterests("Therapying the dogs, no bamboozlin");
        emp2.setEmployeeOfWeekCount(8);
        emp2.setHireDate(LocalDate.parse("2018-10-25"));
        emp2.setRole(role);
        emp2.setUserName("tbates");
        emp2.setPassword("borkbork");
        empDao.addEmployee(emp2);

        assertEquals(2, empDao.getEmployeesByRoleId(role.getRoleId()).size());

        Role role2 = new Role();
        role2.setRoleType("Event Committee");
        roleDao.addRole(role2);
        emp2.setRole(role2);
        empDao.updateEmployee(emp2);

        assertEquals(1, empDao.getEmployeesByRoleId(role.getRoleId()).size());
        assertEquals(1, empDao.getEmployeesByRoleId(role2.getRoleId()).size());
    }
}
