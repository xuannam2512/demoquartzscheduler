package com.xuannam.quartzcheduler.demo.service;

import com.xuannam.quartzcheduler.demo.model.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    List<User> getUsers();
}
