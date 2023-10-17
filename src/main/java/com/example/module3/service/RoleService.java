package com.example.module3.service;

import com.example.module3.dao.RoleDAO;
import com.example.module3.model.Role;

import java.util.ArrayList;
import java.util.List;

public class RoleService {
    private RoleDAO roleDAO;
    public RoleService(){
        roleDAO = new RoleDAO();
    }
    public List<Role> getAllRole(){
        return roleDAO.findAll();
    }
    public Role findById(int id){
        return roleDAO.findById(id);
    }
}
