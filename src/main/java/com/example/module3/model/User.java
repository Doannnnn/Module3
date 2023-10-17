package com.example.module3.model;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

public class User {
    private int id;
    private String name;
    private String userName;
    private String password;
    private String email;
    private Date dob;
    private EGender gender;
    private Role role;
    private boolean deleted = false;

    public User(){

    }

    public User(String name, String userName, String password, String email, Date dob, EGender gender) {
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.dob = dob;
        this.gender = gender;
    }

    public User(int id, String name, String userName, String password, String email, Date dob, EGender gender, Role role, boolean deleted) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.dob = dob;
        this.gender = gender;
        this.role = role;
        this.deleted = deleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public EGender getGender() {
        return gender;
    }

    public void setGender(EGender gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
