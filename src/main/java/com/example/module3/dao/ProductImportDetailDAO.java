package com.example.module3.dao;

import com.example.module3.model.Product;
import com.example.module3.model.ProductImportDetail;
import com.example.module3.service.dto.ProductImportListResponse;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductImportDetailDAO extends DatabaseConnection{
    public void createImportDetail(int productImportId, int productId, int quantity, BigDecimal amount){
        String CREATE = "INSERT INTO `manageruser`.`product_import_detail` (`amount`, `quantity`, `product_id`, `product_import_id`) VALUES (?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE)) {
            preparedStatement.setBigDecimal(1, amount);
            preparedStatement.setInt(2, quantity);
            preparedStatement.setInt(3, productId);
            preparedStatement.setInt(4, productImportId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
    }
    public void deleteImportDetailById(int id){
        String DELETE_IMPORT_DETAIL_ID = "DELETE FROM `manageruser`.`product_import_detail` WHERE (`product_import_id` = ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_IMPORT_DETAIL_ID)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
    }

    public void delete(int id){
        String DELETE = "DELETE FROM `manageruser`.`product_import_detail` WHERE (`product_import_id` = ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<ProductImportDetail> findById(int id){
        var result = new ArrayList<ProductImportDetail>();
        String SELECT_ALL = "SELECT p.`name` AS products, pid.quantity, pid.amount " +
                "FROM product_import_detail pid " +
                "LEFT JOIN product_import pi ON pi.id = pid.product_import_id " +
                "LEFT JOIN products p ON p.id = pid.product_id " +
                "WHERE pi.id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            var rs = preparedStatement.executeQuery();
            while (rs.next()) {
                result.add(new ProductImportDetail(
                        new Product(rs.getString("products")),
                        rs.getInt("quantity"),
                        rs.getBigDecimal("amount")
                ));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return result;
    }
}
