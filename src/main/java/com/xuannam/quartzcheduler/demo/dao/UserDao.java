package com.xuannam.quartzcheduler.demo.dao;

import com.xuannam.quartzcheduler.demo.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<User, Integer>
{
}
