package com.example.module3.dao;

import com.example.module3.model.Author;
import com.example.module3.model.Book;
import com.example.module3.model.Category;
import com.example.module3.model.Product;
import com.example.module3.service.dto.Page;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookDAO extends DatabaseConnection{
    public Page<Book> findAll(int page, boolean isShowRestore, String search){
        Page<Book> result = new Page<Book>();
        final int TOTAL_ELEMENT = 5;
        result.setCurrentPage(page);
        ArrayList<Book> content = new ArrayList<Book>();
        if(search == null){
            search = "";
        }
        search = "%" + search.toLowerCase() + "%";
        final int DELETED = isShowRestore ? 1 : 0;
        String SELECT_ALL = "SELECT b.*, a.name author_name " +
                "FROM books b JOIN authors a on a.id = b.author_id  WHERE b.deleted = ? AND " +
                "(LOWER(b.title) LIKE ? OR LOWER(b.description) LIKE ? OR LOWER(a.name) LIKE ?) " +
                " LIMIT ? OFFSET ?";

        String SELECT_COUNT = "SELECT COUNT(1) cnt FROM books b " +
                "JOIN authors a on a.id = b.author_id WHERE b.deleted = ? AND " +
                "(LOWER(b.title) LIKE ? OR LOWER(b.description) LIKE ? OR LOWER(a.name) LIKE ?) ";
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
                content.add(getBookByResultSet(rs));
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

    public void create(Book book){
        String CREATE = "INSERT INTO `manageruser`.`books` (`title`, `description`, `price`, `publishDate`, `author_id`) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getDescription());
            preparedStatement.setBigDecimal(3, book.getPrice());
            preparedStatement.setDate(4, book.getPublishDate());
            preparedStatement.setInt(5, book.getAuthor().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
    }
    public void update(Book book){
        String UPDATE = "UPDATE `manageruser`.`books` " +
                "SET `title` = ?, `description` = ?, `price` = ?, `publishDate` = ? , `author_id` = ? " +
                "WHERE (`id` = ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getDescription());
            preparedStatement.setBigDecimal(3, book.getPrice());
            preparedStatement.setDate(4, book.getPublishDate());
            preparedStatement.setInt(5, book.getAuthor().getId());
            preparedStatement.setInt(6, book.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
    }
    public void delete(int id){
        String DELETE = "UPDATE `manageruser`.`books` " +
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
        String DELETE = "UPDATE `manageruser`.`books` " +
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


    public Book findById(int id){
        String SELECT_BY_ID = "SELECT b.*, a.name author_name " +
                "FROM books b JOIN authors a on " +
                "a.id = b.author_id " +
                "WHERE b.id = ? and b.deleted = '0'";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return getBookByResultSet(rs);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    private Book getBookByResultSet(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt("id"));
        book.setTitle(rs.getString("title"));
        book.setDescription(rs.getString("description"));
        book.setPrice(rs.getBigDecimal("price"));
        book.setPublishDate(rs.getDate("publishDate"));
        book.setAuthor(new Author(rs.getInt("author_id"), rs.getString("author_name")));
        return book;
    }
}
