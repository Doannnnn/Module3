package com.example.module3.dao;

import com.example.module3.model.ProductImport;
import com.example.module3.model.ProductImportDetail;
import com.example.module3.model.User;
import com.example.module3.service.dto.ProductImportListResponse;
import com.example.module3.service.dto.ProductImportResponse;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductImportDAO extends DatabaseConnection{
    public int create(ProductImport productImport){
        String CREATE = "INSERT INTO `manageruser`.`product_import` (`code`, `import_date`, `total_amount`) " +
                "VALUES (?, ?, ?)";
        String SELECT_MAX_ID = "SELECT MAX(id) as max_id FROM `manageruser`.`product_import`";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE)) {
            preparedStatement.setString(1, productImport.getCode());
            preparedStatement.setDate(2, productImport.getImportDate());
            preparedStatement.setBigDecimal(3, productImport.getTotalAmount());
            preparedStatement.executeUpdate();
            PreparedStatement statementId = connection.prepareStatement(SELECT_MAX_ID);
            var rs = statementId.executeQuery();
            if(rs.next()){
                return rs.getInt("max_id");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
        return -1;
    }

    public ProductImport findById(int id){
        String SELECT_BY_ID = "SELECT pi.* " +
                "FROM product_import pi " +
                "WHERE pi.id = ?";
        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ProductImport productImport = new ProductImport();
                productImport.setId(resultSet.getInt("id"));
                productImport.setCode(resultSet.getString("code"));
                productImport.setImportDate(resultSet.getDate("import_date"));
                productImport.setTotalAmount(resultSet.getBigDecimal("total_amount"));
                return productImport;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ProductImport();
    }

    public void update(ProductImport productImport){
        String UPDATE = "UPDATE `manageruser`.`product_import` SET `code` = ?, `import_date` = ?, `total_amount` = ? WHERE (`id` = ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, productImport.getCode());
            preparedStatement.setDate(2, productImport.getImportDate());
            preparedStatement.setBigDecimal(3, productImport.getTotalAmount());
            preparedStatement.setInt(4, productImport.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
    }

    public void delete(int id){
        String DELETE = "DELETE FROM `manageruser`.`product_import` WHERE (`id` = ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<ProductImportListResponse> findAll(){
        var result = new ArrayList<ProductImportListResponse>();
        String SELECT_ALL = "SELECT pi.id, pi.`code`, pi.import_date, GROUP_CONCAT(p.`name`) products, pi.total_amount FROM " +
                "product_import pi " +
                "LEFT JOIN product_import_detail pid on pi.id = pid.product_import_id " +
                "LEFT JOIN products p on p.id = pid.product_id GROUP BY pi.id";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            System.out.println(preparedStatement);
            var rs = preparedStatement.executeQuery();
            while (rs.next()) {
                result.add(new ProductImportListResponse(
                        rs.getInt("id"),
                        rs.getString("code"),
                        rs.getDate("import_date"),
                        rs.getString("products"),
                        rs.getBigDecimal("total_amount")
                ));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return result;
    }
}
