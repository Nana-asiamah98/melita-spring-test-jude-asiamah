package com.ml.ordermicroservice.service;

import com.ml.ordermicroservice.model.Role;
import com.ml.ordermicroservice.model.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    Role saveRole(Role role);

    void addUserToRole(String username, String roleName);

    User getUser(String username);

    List<User> getAllUsers();
}
