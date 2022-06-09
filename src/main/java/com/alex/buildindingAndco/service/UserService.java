package com.alex.buildindingAndco.service;

import com.alex.buildindingAndco.model.User;

import java.util.List;

public interface UserService {

    List<User> getAll();

    User getById(Integer id);

    User getByUsername(String username);

    User createUser(User user);

    User getUserByUsernameAndPassword(String username, String password);

    void deleteUser(Integer id);

    User update(User user);

}
