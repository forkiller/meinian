package com.atguigu.service;

import com.atguigu.pojo.User;

/**
 * @author steve
 * @since 2021-07-07 23:39
 */

@SuppressWarnings("all")
public interface UserService {
    User findUserByUsername(String username);

}
