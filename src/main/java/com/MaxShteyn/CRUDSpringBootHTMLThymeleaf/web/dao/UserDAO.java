package com.MaxShteyn.CRUDSpringBootHTMLThymeleaf.web.dao;

import com.MaxShteyn.CRUDSpringBootHTMLThymeleaf.web.model.User;

import java.util.List;

public interface UserDAO {
    public boolean validateUser(String login, String password);
    public User getUserByLogin(String login);
    public void addUser(User user);
    public void updateUser(User user);
    public void deleteUser(String login);
    public List<User> getAllUsers();
}
