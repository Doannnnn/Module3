package com.example.module3.controller;

import com.example.module3.model.Category;
import com.example.module3.model.Product;
import com.example.module3.service.CategoryService;
import com.example.module3.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet(name = "productController", urlPatterns = "/product")
public class ProductController extends HttpServlet {
    private ProductService productService;
    private CategoryService categoryService;
    @Override
    public void init() throws ServletException {
        productService = new ProductService();
        categoryService = new CategoryService();

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
        productService.delete(Integer.parseInt(req.getParameter("id")));
        resp.sendRedirect("/product?message=Deleted");
    }

    private void showRestore(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        showTable(req, true, resp);
    }

    private void showEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("product", productService.findById(Integer.parseInt(req.getParameter("id"))));
        req.setAttribute("categories", categoryService.getCategories());
        req.getRequestDispatcher("product/create.jsp").forward(req,resp);
    }

    private void showList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        showTable(req, false, resp);
    }

    private void showTable(HttpServletRequest req, boolean isShowRestore, HttpServletResponse resp) throws ServletException, IOException {
        String pageString = req.getParameter("page");
        if (pageString == null) {
            pageString = "1";
        }
        req.setAttribute("page", productService.getProducts(Integer.parseInt(pageString), isShowRestore, req.getParameter("search")));
        req.setAttribute("message", req.getParameter("message"));
        req.setAttribute("isShowRestore", isShowRestore);
        req.setAttribute("search", req.getParameter("search"));
        req.getRequestDispatcher("product/index.jsp").forward(req, resp);
    }

    private void showCreate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("product", new Product());
        req.setAttribute("categories", categoryService.getCategories());
        req.getRequestDispatcher("product/create.jsp").forward(req,resp);
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
        productService.restore(req.getParameterValues("restore"));
        resp.sendRedirect("/product?message=Restored&action=restore");
    }

    private void edit(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        productService.update(getProductByRequest(req), Integer.parseInt(req.getParameter("id")));
        resp.sendRedirect("/product?message=Updated");
    }

    private void create(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        productService.create(getProductByRequest(req));
        resp.sendRedirect("/product?message=Created");
    }
    private Product getProductByRequest(HttpServletRequest req){
        String name = req.getParameter("name");
        BigDecimal price = new BigDecimal(req.getParameter("price"));
        String description = req.getParameter("description");
        String idCategory = req.getParameter("category");
        Category category = new Category(Integer.parseInt(idCategory));
        return new Product(name, price,description,category);
    }
}
