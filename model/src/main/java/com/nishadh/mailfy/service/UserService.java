package com.nishadh.mailfy.service;

import com.nishadh.mailfy.model.User;

import javax.persistence.NoResultException;
import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Created by nishadh on 12/2/15.
 */
public interface UserService {
    User findById(Integer id) throws NoResultException;
    User findByUserEmail(String email) throws NoResultException;
    User findByEmailAndPassword(String email, String password) throws NoResultException;
    void create(User user);
    void update(User user);
    void delete(Integer id);
    Collection<User> findAll();
}
