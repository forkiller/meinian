package com.atguigu.service;

import com.atguigu.pojo.Address;

import java.util.List;

/**
 * @author steve
 * @since 2021-07-09 0:10
 */

@SuppressWarnings("all")
public interface AddressService {
    List<Address> findAllMaps();
}
