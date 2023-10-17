package com.example.module3.controller;

import com.example.module3.model.EGender;
import com.example.module3.model.Role;
import com.example.module3.model.User;
import com.example.module3.service.RoleService;
import com.example.module3.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

@WebServlet(name = "authController", urlPatterns = "/auth")
public class AuthController extends HttpServlet {
    private UserService userService;

    private RoleService roleService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if(action == null){
            action = "";
        }
        switch (action) {
            case "register" -> showRegister(req, resp);
            default -> showLogin(req,resp);

        }
    }
    private void showLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("message", req.getParameter("message"));
        req.getRequestDispatcher("/auth/login.jsp").forward(req,resp);
    }

    private void showRegister(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("roles", roleService.getAllRole());
        req.setAttribute("genders", EGender.values());
        req.setAttribute("message", req.getParameter("message"));
        req.getRequestDispatcher("auth/register.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if(action == null){
            action = "";
        }
        switch (action) {
            case "register" -> register(req, resp);
            default -> login(req,resp);
        }
    }

    private void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if(!userService.login(req,resp)){
            resp.sendRedirect("/auth?message=Invalid username or password");
        }
    }

    private void register(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        userService.register(getUserByRequest(req));
        resp.sendRedirect("/auth?message=Register Success");
    }
    private User getUserByRequest(HttpServletRequest req){
        String name = req.getParameter("name");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        Date dob = Date.valueOf(req.getParameter("dob"));
        EGender gender = EGender.valueOf(req.getParameter("gender"));
        String idRole = req.getParameter("role");
        return new User(name,username,password,email,dob,gender);
    }

    @Override
    public void init() throws ServletException {
        userService = new UserService();
        roleService = new RoleService();
    }
}
