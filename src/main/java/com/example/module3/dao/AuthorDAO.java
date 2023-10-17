package com.example.module3.dao;

import com.example.module3.model.Author;
import com.example.module3.model.Category;
import com.example.module3.service.AuthorService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO extends DatabaseConnection{
    public List<Author> findAll(){
        List<Author> results = new ArrayList<>();
        String SELECT_ALL = "SELECT * FROM authors";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                results.add(new Author(rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
        return results;
    }

    public Author findById(int id){
        String SELECT_BY_ID = "SELECT * FROM authors WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return new Author(rs.getInt("id"), rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
        return null;
    }

}