package com.atguigu.dao;

import com.atguigu.pojo.User;

/**
 * @author steve
 * @since 2021-07-07 23:52
 */

@SuppressWarnings("all")
public interface UserDao {
    User findUserByUsername(String username);
}
