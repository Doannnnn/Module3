package com.example.module3.dao;

import com.example.module3.model.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDAO extends DatabaseConnection{
    public List<Role> findAll(){
        List<Role> results = new ArrayList<>();
        String SELECT_ALL = "SELECT * FROM roles";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                results.add(new Role(rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
        return results;
    }
    public Role findById(int id){
        String SELECT_BY_ID = "SELECT * FROM roles WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return new Role(rs.getInt("id"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
        return null;
    }
}
