package com.atguigu.dao;

import com.atguigu.pojo.Role;

/**
 * @author steve
 * @since 2021-07-08 0:00
 */


public interface RoleDao {

    /**
     * @param id
     * @return
     */
    Role findRoleByUserId(Integer id);
}
