package com.example.module3.controller;

import com.example.module3.model.ProductImport;
import com.example.module3.model.ProductImportDetail;
import com.example.module3.service.ProductImportDetailService;
import com.example.module3.service.ProductImportService;
import com.example.module3.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "productImportController", urlPatterns = "/product-import")
public class ProductImportController extends HttpServlet {
    private ProductImportService productImportService;
    private ProductImportDetailService productImportDetailService;
    private ProductService productService;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if(action == null) {
            action = "";
        }
        switch (action){
            case "create":
                showCreate(req, resp);
                break;
            case "edit":
                showEdit(req, resp);
                break;
            case "delete":
                delete(req, resp);
                break;
            default:
                showList(req, resp);
        }
    }

    private void showCreate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var products = productService.findAll();
        req.setAttribute("products", products);
        req.setAttribute("productsJSON", new ObjectMapper().writeValueAsString(products));
        req.getRequestDispatcher("product-import/create.jsp").forward(req,resp);
    }

    private void showEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        req.setAttribute("productImport", productImportService.findById(id));
        var products = productService.findAll();
        req.setAttribute("products", products);
        req.setAttribute("productImportDetails", productImportDetailService.findById(id));
        req.setAttribute("productsJSON", new ObjectMapper().writeValueAsString(products));
        req.getRequestDispatcher("product-import/edit.jsp").forward(req,resp);
    }

    private void showList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("productImports", productImportService.findAll());
        req.setAttribute("message",req.getParameter("message"));
        req.getRequestDispatcher("product-import/index.jsp").forward(req,resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if(action == null) {
            action = "";
        }
        switch (action){
            case "create":
                create(req, resp);
                break;
            case "edit":
                edit(req, resp);
                break;
        }
    }

    private void edit(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        productImportService.edit(req);
        resp.sendRedirect("/product-import?message=Edit Successfully");
    }

    private void create(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        productImportService.create(req);
        resp.sendRedirect("/product-import?message=Created Successfully");
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        productImportService.delete(req);
        resp.sendRedirect("/product-import?message=Delete Successfully");
    }

    @Override
    public void init() throws ServletException {
        productImportService = new ProductImportService();
        productService = new ProductService();
        productImportDetailService = new ProductImportDetailService();
    }
}
