package com.example.module3.service;

import com.example.module3.dao.AuthorDAO;
import com.example.module3.model.Author;
import com.example.module3.model.Category;

import java.util.List;

public class AuthorService {
    private final AuthorDAO authorDAO;

    public AuthorService() {
        authorDAO = new AuthorDAO();
    }

    public List<Author> getAuthors(){
        return authorDAO.findAll();
    }

    public Author getAuthor(int id){
        return authorDAO.findById(id);
    }
}
