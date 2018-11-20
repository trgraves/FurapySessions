/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.furapysessions.model;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Thomas Graves
 */
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@ToString @EqualsAndHashCode
public class Employee {
    private int employeeId;
    private String firstName;
    private String lastName;
    private String title;
    private String partner;
    private String interests;
    private int employeeOfWeekCount;
    private LocalDate hireDate;
    private Role role;
    private String userName;
    private String password;
    private String pictureFile;
    private boolean isEOTW;
}
