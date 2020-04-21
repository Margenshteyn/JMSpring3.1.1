package com.MaxShteyn.CRUDSpringBootHTMLThymeleaf.web.dao;

import com.MaxShteyn.CRUDSpringBootHTMLThymeleaf.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean validateUser(String login, String password) {
        User user = getUserByLogin(login);
        if (user != null) {
            return passwordEncoder.matches(password, user.getPassword());// ВАЖЕН порядок!!! сначала обычный потом кодированный("test4", $2a$10$Ivh69UhSnS8KCx2Ymph7Cenc1ARRJoepNP5RmHD5VkghN.W.GYttG);
        }
        return false;
    }

    @Override
    public User getUserByLogin(String login) {
//        return sessionFactory.openSession().get(User.class, login);
        return entityManager.find(User.class, login);
    }

    @Override
    public void addUser(User user) {
//        sessionFactory.getCurrentSession().save(user);
        entityManager.persist(user);
    }

    @Override
    public void updateUser(User user) {
//        sessionFactory.getCurrentSession().update(user);
        entityManager.merge(user); // для сохранения связных сущностей
      /*  entityManager
                .createQuery("UPDATE User SET name =:pName, password =:pPass WHERE login =:l")
                .setParameter("pName", name)
                .setParameter("pPass", password)
                .setParameter("l", login)
                .executeUpdate();*/
    }

    @Override
    public void deleteUser(String login) {
//        sessionFactory.getCurrentSession().delete(sessionFactory.getCurrentSession().get(User.class, login));
        entityManager.remove(getUserByLogin(login));
    }

    @Override
    public List<User> getAllUsers() {
       /* TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("FROM User");
        return query.getResultList();*/
       return entityManager.createQuery("FROM User", User.class).getResultList();
    }
}
