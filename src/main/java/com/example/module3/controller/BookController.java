package com.example.module3.controller;

import com.example.module3.model.Author;
import com.example.module3.model.Book;
import com.example.module3.model.Category;
import com.example.module3.model.Product;
import com.example.module3.service.AuthorService;
import com.example.module3.service.BookService;
import com.example.module3.service.CategoryService;
import com.example.module3.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;

@WebServlet(name = "bookController", urlPatterns = "/book")
public class BookController extends HttpServlet {
    private BookService bookService;
    private AuthorService authorService;
    @Override
    public void init() throws ServletException {
        bookService = new BookService();
        authorService = new AuthorService();

    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if(action == null){
            action = "";
        }
        switch (action) {
            case "create" -> showCreate(req, resp);
            case "edit" -> showEdit(req, resp);
            case "restore" -> showRestore(req, resp);
            case "delete" -> delete(req, resp);
            default -> showList(req, resp);
        }
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        bookService.delete(Integer.parseInt(req.getParameter("id")));
        resp.sendRedirect("/book?message=Deleted");
    }

    private void showRestore(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        showTable(req, true, resp);
    }

    private void showEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("book", bookService.findById(Integer.parseInt(req.getParameter("id"))));
        req.setAttribute("authors", authorService.getAuthors());
        req.getRequestDispatcher("book/create.jsp").forward(req,resp);
    }

    private void showList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        showTable(req, false, resp);
    }

    private void showTable(HttpServletRequest req, boolean isShowRestore, HttpServletResponse resp) throws ServletException, IOException {
        String pageString = req.getParameter("page");
        if (pageString == null) {
            pageString = "1";
        }
        req.setAttribute("page", bookService.getBooks(Integer.parseInt(pageString), isShowRestore, req.getParameter("search")));
        req.setAttribute("message", req.getParameter("message"));
        req.setAttribute("isShowRestore", isShowRestore);
        req.setAttribute("search", req.getParameter("search"));
        req.getRequestDispatcher("book/index.jsp").forward(req, resp);
    }

    private void showCreate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("book", new Book());
        req.setAttribute("authors", authorService.getAuthors());
        req.getRequestDispatcher("book/create.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if(action == null){
            action = "";
        }
        switch (action) {
            case "create" -> create(req, resp);
            case "edit" -> edit(req, resp);
            case "restore" -> restore(req, resp);
        }
    }

    private void restore(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        bookService.restore(req.getParameterValues("restore"));
        resp.sendRedirect("/book?message=Restored&action=restore");
    }

    private void edit(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        bookService.update(getBooktByRequest(req), Integer.parseInt(req.getParameter("id")));
        resp.sendRedirect("/book?message=Updated");
    }

    private void create(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        bookService.create(getBooktByRequest(req));
        resp.sendRedirect("/book?message=Created");
    }
    private Book getBooktByRequest(HttpServletRequest req){
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        BigDecimal price = new BigDecimal(req.getParameter("price"));
        Date publishDate = Date.valueOf(req.getParameter("publishDate"));
        String idAuthor = req.getParameter("author");
        Author author = new Author(Integer.parseInt(idAuthor));
        return new Book(title, description, price,publishDate, author);
    }
}
