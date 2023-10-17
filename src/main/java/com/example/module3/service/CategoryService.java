package com.example.module3.service;

import com.example.module3.dao.CategoryDAO;
import com.example.module3.model.Category;

import java.util.List;

public class CategoryService {
    private final CategoryDAO categoryDAO;

    public CategoryService() {
        categoryDAO = new CategoryDAO();
    }

    public List<Category> getCategories(){
        return categoryDAO.findAll();
    }

    public Category getCategory(int id){
        return categoryDAO.findById(id);
    }
}
