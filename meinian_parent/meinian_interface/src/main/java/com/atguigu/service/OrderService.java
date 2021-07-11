package com.atguigu.service;

import com.atguigu.enetity.Result;

import java.util.Map;

/**
 * @author steve
 * @since 2021-07-05 19:33
 */

@SuppressWarnings("all")
public interface OrderService {

    Result order(Map map) throws Exception;

    Map findById(Integer id) throws Exception;
}
