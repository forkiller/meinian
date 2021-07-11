package com.atguigu.dao;

import com.atguigu.pojo.Permission;

/**
 * @author steve
 * @since 2021-07-08 0:05
 */

@SuppressWarnings("all")
public interface PermissionDao {

    Permission findPermissionByRoleId(Integer id);
}
