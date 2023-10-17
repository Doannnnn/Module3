package com.example.module3.service;

import com.example.module3.dao.UserDAO;
import com.example.module3.model.User;
import com.example.module3.util.PasswordEncryptionUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class UserService {
    private final UserDAO userDAO;
    public UserService(){
        userDAO = new UserDAO();
    }
    public List<User> getAllUser(boolean delete){
        return userDAO.selectAllUsers(delete);
    }
    public User findById(int id){
        return userDAO.findById(id);
    }
    public void create(User user){
        user.setPassword(PasswordEncryptionUtil.encryptPassword(user.getPassword()));
         userDAO.create(user);
    }
    public void update(User user, int id){
        user.setId(id);
        userDAO.update(user);
    }
    public void delete(int id){
        userDAO.delete(id);
    }
    public void restore(int id){
        userDAO.restore(id);
    }
    public void restoreAll(List<Integer> ids){
        for (Integer id : ids) {
            userDAO.restore(id);
        }
    }
    public void register(User user){
        user.setPassword(PasswordEncryptionUtil.encryptPassword(user.getPassword()));
        userDAO.register(user);
    }
    public boolean login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String usernameOrEmail = req.getParameter("username");
        String password = req.getParameter("password");
        var user = userDAO.findByUsernameOrEmail(usernameOrEmail);
        if(user != null && PasswordEncryptionUtil.checkPassword(password, user.getPassword())){
            HttpSession session = req.getSession();
            session.setAttribute("user", user); // luu vo kho
            if(user.getRole().getName().equals("ADMIN")){
                resp.sendRedirect("/book?message=Login Success");
            }else{
                resp.sendRedirect("/product?message=Login Success");
            }
            return true;
        }
        return false;
    }
}
