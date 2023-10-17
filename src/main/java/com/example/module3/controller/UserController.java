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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "userController", urlPatterns = "/user")
public class UserController extends HttpServlet {
    private UserService userService;
    private RoleService roleService;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if(action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                showCreate(req, resp);
                break;
            case "edit":
                showEdit(req, resp);
                break;
            case "delete":
                delete(req, resp);
                break;
            case "showRestore":
                showRestore(req, resp);
                break;
            case "restore":
                restore(req, resp);
                break;
            default:
                showList(req, resp);
        }
    }

    private void restoreAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String[] selectedUserIds = req.getParameterValues("selectedUsers");

        if (selectedUserIds == null || selectedUserIds.length == 0) {
            resp.sendRedirect("/user?message=Please select at least one user to restore.&action=showRestore");
        } else {
            List<Integer> userIds = new ArrayList<>();
            for (String userId : selectedUserIds) {
                userIds.add(Integer.parseInt(userId));
            }
            userService.restoreAll(userIds);
            resp.sendRedirect("/user?message=Restored");
        }
    }

    private void showRestore(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("users", userService.getAllUser(true));
        req.setAttribute("message", req.getParameter("message"));
        req.getRequestDispatcher("user/restore.jsp").forward(req, resp);
    }
    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        userService.delete(Integer.parseInt(req.getParameter("id")));
        resp.sendRedirect("/user?message=Delete");
    }

    private void showEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("user",userService.findById(Integer.parseInt(req.getParameter("id"))));
        req.setAttribute("genders", EGender.values());
        req.setAttribute("roles", roleService.getAllRole());
        req.getRequestDispatcher("user/edit.jsp").forward(req, resp);
    }

    private void showCreate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("genders", EGender.values());
        req.setAttribute("roles", roleService.getAllRole());
        req.getRequestDispatcher("user/create.jsp").forward(req, resp);
    }

    private void showList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("users", userService.getAllUser(false));

        req.setAttribute("message", req.getParameter("message"));
        req.getRequestDispatcher("user/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    create(req, resp);
                    break;
                case "edit":
                    edit(req, resp);
                    break;
                case "restoreAll":
                    restoreAll(req, resp);
                    break;
                default:
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
                    break;
            }
        } catch (SQLException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        }
    }
    private void edit(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        Date dob = Date.valueOf(req.getParameter("dob"));
        EGender gender = EGender.valueOf(req.getParameter("gender"));
        String idRole = req.getParameter("role");
        Role role = new Role(Integer.parseInt(idRole));
        User user = new User();
        user.setName(name);
        user.setUserName(username);
        user.setEmail(email);
        user.setDob(dob);
        user.setGender(gender);
        user.setRole(role);
        userService.update(user, id);
        resp.sendRedirect("/user?message=Update");
    }

    private void create(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        String name = req.getParameter("name");
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        Date dob = Date.valueOf(req.getParameter("dob"));
        EGender gender = EGender.valueOf(req.getParameter("gender"));
        String idRole = req.getParameter("role");
        Role role = new Role(Integer.parseInt(idRole));
        User user = new User();
        user.setName(name);
        user.setUserName(username);
        user.setEmail(email);
        user.setDob(dob);
        user.setGender(gender);
        user.setRole(role);
        userService.create(user);
        resp.sendRedirect("/user?message=Create");
    }
    private void restore(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        userService.restore(Integer.parseInt(req.getParameter("id")));
        resp.sendRedirect("/user?message=Restored&action=showRestore");
    }
    @Override
    public void init() throws ServletException {
        userService = new UserService();
        roleService = new RoleService();
    }
}
