package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.pojo.Address;
import com.atguigu.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author steve
 * @since 2021-07-09 0:08
 */

@SuppressWarnings("all")
@RestController
@RequestMapping("/address")
public class AddressController {

    @Reference
    private AddressService addressService;

    @RequestMapping("/findAllMaps")
    public Map findAllMaps() {
        Map map = new HashMap();
        List<Address> list = addressService.findAllMaps();

        //1、定义分店坐标集合
        List<Map> gridnMaps = new ArrayList<>();
        //2、定义分店名称集合
        List<Map> nameMaps = new ArrayList();

        


        // 存放经纬度
        map.put("gridnMaps", gridnMaps);
        // 存放名字
        map.put("nameMaps", nameMaps);
        return map;
    }
}
