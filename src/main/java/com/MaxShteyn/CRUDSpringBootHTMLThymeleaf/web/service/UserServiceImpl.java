package com.MaxShteyn.CRUDSpringBootHTMLThymeleaf.web.service;

import com.MaxShteyn.CRUDSpringBootHTMLThymeleaf.web.dao.UserDAO;
import com.MaxShteyn.CRUDSpringBootHTMLThymeleaf.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public boolean addUser(User user) {
        if (getUserByLogin(user.getUsername()) != null) {
            return false;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDAO.addUser(user);
        return true;
    }

    public User getUserByLogin(String login) {
         return userDAO.getUserByLogin(login);
    }

    @Transactional
    @Override
    public boolean updateUser(User user, String password) {
        if (userDAO.validateUser(user.getUsername(), password)) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userDAO.updateUser(user);
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public void deleteUser(String login) {
        userDAO.deleteUser(login);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getUsersList() {
        return userDAO.getAllUsers();
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String login) {
        Optional<User> currentUser = Optional.ofNullable(userDAO.getUserByLogin(login));
        return currentUser.orElseThrow(IllegalArgumentException::new);// IllegalAccessError
    }
}
