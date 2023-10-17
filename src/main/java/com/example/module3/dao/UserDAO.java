package com.example.module3.dao;

import com.example.module3.model.EGender;
import com.example.module3.model.Role;
import com.example.module3.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends DatabaseConnection {
    private final String SELECT_ALL_USERS = "SELECT u.*, r.name AS role_name FROM users u JOIN  roles r ON r.id = u.role_id WHERE u.deleted = ? GROUP BY u.id;";
    private final String INSERT_USERS_SQL = "INSERT INTO `manageruser`.`users` (`name`, `user_name`, `password`, `email`, `dob`, `gender`, `role_id`) VALUES (?, ?,?, ?, ?, ?, ?);";
    private final String UPDATE_USERS_SQL = "UPDATE `manageruser`.`users` SET `name` = ?, `user_name` = ?, `email` = ?, `dob` = ?, `gender` = ?, `role_id` = ? WHERE (`id` = ?);";
    private final String SELECT_BY_ID = "SELECT u.*, r.name AS role_name FROM users u JOIN  roles r ON r.id = u.role_id WHERE u.id = ?;";
    private final String DELETE_USERS_SQL = "UPDATE `manageruser`.`users` SET `deleted` = 1 WHERE (`id` = ?);";
    private final String RESTORE_USERS_SQL = "UPDATE `manageruser`.`users` SET `deleted` = 0 WHERE (`id` = ?);";

    public List<User> selectAllUsers(boolean deleted) {
        List<User> users = new ArrayList<>();
        final int DELETED = deleted ? 1 : 0;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS)) {
            preparedStatement.setInt(1, DELETED);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setUserName(rs.getString("user_name"));
                user.setEmail(rs.getString("email"));
                user.setDob(rs.getDate("dob"));
                user.setGender(EGender.valueOf(rs.getString("gender")));
                user.setRole(new Role(rs.getInt("id"), rs.getString("role_name")));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }
    public User findById(int id){
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return getUserByResultSet(rs);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    private User getUserByResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setUserName(rs.getString("user_name"));
        user.setEmail(rs.getString("email"));
        user.setDob(rs.getDate("dob"));
        user.setGender(EGender.valueOf(rs.getString("gender")));
        user.setRole(new Role(rs.getInt("role_id"), rs.getString("role_name")));
        return user;
    }
    public void create(User user){
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getUserName());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setDate(5, user.getDob());
            preparedStatement.setString(6, String.valueOf(user.getGender()));
            preparedStatement.setInt(7, user.getRole().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
    }
    public void update(User user){
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USERS_SQL)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getUserName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setDate(4, user.getDob());
            preparedStatement.setString(5, String.valueOf(user.getGender()));
            preparedStatement.setInt(6, user.getRole().getId());
            preparedStatement.setInt(7, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
    }
    public void delete(int id){
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USERS_SQL)) {
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void restore(int id){
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(RESTORE_USERS_SQL)) {
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void register(User user){
        final String REGISTER_USER = "INSERT INTO `manageruser`.`users` (`name`, `user_name`, `password`, `email`, `dob`, `gender`, `role_id`) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(REGISTER_USER);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getUserName());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setDate(5, user.getDob());
            preparedStatement.setString(6, user.getGender().toString());
            preparedStatement.setInt(7, 1);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
    }
    public User findByUsernameOrEmail(String usernameOrEmail){
        var SELECT_BY_ID = "SELECT u.*, r.name as role_name " +
                "FROM users u JOIN roles r on " +
                "u.role_id = r.id " +
                "WHERE (u.user_name = ? or u.email = ?) and u.deleted = '0'";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setString(1, usernameOrEmail);
            preparedStatement.setString(2, usernameOrEmail);
            System.out.println(preparedStatement);
            var rs = preparedStatement.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setPassword(rs.getString("password"));
                user.setUserName(rs.getString("user_name"));
                user.setEmail(rs.getString("email"));
                user.setGender(EGender.valueOf(rs.getString("gender")));

                return user;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
