package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.AddressDao;
import com.atguigu.pojo.Address;
import com.atguigu.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author steve
 * @since 2021-07-09 8:38
 */

@SuppressWarnings("all")
@Service(interfaceClass = AddressService.class)
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressDao addressDao;

    @Override
    public List<Address> findAllMaps() {
        addressDao.findAllMaps();
        return null;
    }
}
