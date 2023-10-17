package com.example.module3.dao;


import com.example.module3.model.Category;
import com.example.module3.model.Product;
import com.example.module3.service.dto.Page;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO  extends DatabaseConnection{

    public Page<Product> findAll(int page, boolean isShowRestore, String search){
        Page<Product> result = new Page<Product>();
        final int TOTAL_ELEMENT = 5;
        result.setCurrentPage(page);
        ArrayList<Product> content = new ArrayList<Product>();
        if(search == null){
            search = "";
        }
        search = "%" + search.toLowerCase() + "%";
        final int DELETED = isShowRestore ? 1 : 0;
        String SELECT_ALL = "SELECT p.*, c.name category_name " +
                "FROM products p JOIN categories c on c.id = p.category_id  WHERE p.deleted = ? AND " +
                "(LOWER(p.name) LIKE ? OR LOWER(p.description) LIKE ? OR LOWER(c.name) LIKE ?) " +
                " LIMIT ? OFFSET ?";

        String SELECT_COUNT = "SELECT COUNT(1) cnt FROM products p " +
                "JOIN categories c on c.id = p.category_id WHERE p.deleted = ? AND " +
                "(LOWER(p.name) LIKE ? OR LOWER(p.description) LIKE ? OR LOWER(c.name) LIKE ?) ";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            preparedStatement.setInt(1, DELETED );
            preparedStatement.setString(2, search);
            preparedStatement.setString(3, search);
            preparedStatement.setString(4, search);
            preparedStatement.setInt(5,TOTAL_ELEMENT);
            preparedStatement.setInt(6, (page - 1) * TOTAL_ELEMENT);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                content.add(getProductByResultSet(rs));
            }
            result.setContent(content);
            PreparedStatement preparedStatementCount = connection.prepareStatement(SELECT_COUNT);
            preparedStatementCount.setInt(1,DELETED );
            preparedStatementCount.setString(2, search);
            preparedStatementCount.setString(3, search);
            preparedStatementCount.setString(4, search);
            ResultSet rsCount = preparedStatementCount.executeQuery();
            if(rsCount.next()){
                result.setTotalPage((int) Math.ceil((double) rsCount.getInt("cnt") /TOTAL_ELEMENT));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
        return result;
    }

    public List<Product> findAll(){
        var content = new ArrayList<Product>();
        var SELECT_ALL = "SELECT p.*, c.name category_name " +
                "FROM products p JOIN categories c on c.id = p.category_id  WHERE p.deleted = ? ";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            preparedStatement.setInt(1, 0);
            System.out.println(preparedStatement);
            var rs = preparedStatement.executeQuery();
            while (rs.next()) {
                content.add(getProductByResultSet(rs));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return content;
    }

    public void create(Product product){
        String CREATE = "INSERT INTO `manageruser`.`products` (`name`, `price`, `description`, `category_id`) " +
                "VALUES (?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setBigDecimal(2, product.getPrice());
            preparedStatement.setString(3, product.getDescription());
            preparedStatement.setInt(4, product.getCategory().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
    }
    public void update(Product product){
        String UPDATE = "UPDATE `manageruser`.`products` " +
                "SET `name` = ?, `price` = ?, `description` = ?, `category_id` = ? " +
                "WHERE (`id` = ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setBigDecimal(2, product.getPrice());
            preparedStatement.setString(3, product.getDescription());
            preparedStatement.setInt(4, product.getCategory().getId());
            preparedStatement.setInt(5, product.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
    }
    public void delete(int id){
        String DELETE = "UPDATE `manageruser`.`products` " +
                "SET `deleted` = '1' " +
                "WHERE (`id` = ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
    }
    public void restore(int id){
        String DELETE = "UPDATE `manageruser`.`products` " +
                "SET `deleted` = '0' " +
                "WHERE (`id` = ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
    }


    public Product findById(int id){
        String SELECT_BY_ID = "SELECT p.*, c.name category_name " +
                "FROM products p JOIN categories c on " +
                "c.id = p.category_id " +
                "WHERE p.id = ? and p.deleted = '0'";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return getProductByResultSet(rs);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    private Product getProductByResultSet(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getInt("id"));
        product.setName(rs.getString("name"));
        product.setDescription(rs.getString("description"));
        product.setPrice(rs.getBigDecimal("price"));
        product.setCategory(new Category(rs.getInt("category_id"), rs.getString("category_name")));
        return product;
    }
}
