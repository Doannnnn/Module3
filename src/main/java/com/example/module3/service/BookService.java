package com.example.module3.service;

import com.example.module3.dao.BookDAO;
import com.example.module3.dao.ProductDAO;
import com.example.module3.model.Book;
import com.example.module3.model.Product;
import com.example.module3.service.dto.Page;

public class BookService {
    private final BookDAO bookDAO;

    public BookService() {
        bookDAO = new BookDAO();
    }


    public void create(Book book){
        bookDAO.create(book);
    }

    public Page<Book> getBooks(int page, boolean isShowRestore, String search){
        return bookDAO.findAll(page, isShowRestore, search);
    }

    public Book findById(int id){
        return bookDAO.findById(id);
    }

    public void update(Book book, int id){
        book.setId(id);
        bookDAO.update(book);
    }

    public void restore(String[] ids){
        for (String id : ids) {
            bookDAO.restore(Integer.parseInt(id));
        }
    }

    public void delete(int id){
        bookDAO.delete(id);
    }
}
