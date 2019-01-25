package com.xuannam.quartzcheduler.demo.service;

import com.xuannam.quartzcheduler.demo.dao.UserDao;
import com.xuannam.quartzcheduler.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService
{
    @Autowired private UserDao userDao;

    @Override
    public User createUser(User user) {
        user = userDao.save(user);

        return user;
    }

    @Override
    public List<User> getUsers() {

        return (List<User>) userDao.findAll();
    }
}
