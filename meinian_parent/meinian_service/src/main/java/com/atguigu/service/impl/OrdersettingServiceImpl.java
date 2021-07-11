package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.OrdersettingDao;
import com.atguigu.pojo.OrderSetting;
import com.atguigu.service.OrdersettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author steve
 * @since 2021-07-03 11:02
 */

@SuppressWarnings("all")
@Service(interfaceClass = OrdersettingService.class)
@Transactional
public class OrdersettingServiceImpl implements OrdersettingService {

    @Autowired
    private OrdersettingDao ordersettingDao;

    @Override
    public void add(List<OrderSetting> orderSettings) {
        for (OrderSetting orderSetting : orderSettings) {
            // 判断当前的日期之前是否已经被设置过预约日期，使用当前时间作为条件查询数量
            System.out.println("orderSetting = " + orderSetting.getOrderDate());
            long count = ordersettingDao.findCountByOrderDate(orderSetting.getOrderDate());
            // 如果设置过预约日期，更新number数量
            if (count > 0) {
                ordersettingDao.editNumberByOrderDate(orderSetting);
            } else {
                // 如果没有设置过预约日期，执行保存
                ordersettingDao.add(orderSetting);
            }
        }

        //ordersettingDao.batchAdd(orderSettings);

    }

    @Override
    public List<Map> getOrderSettingByMonth(String date) {
        String dateStart = date + "-1";
        String dateEnd = date + "-31";
        Map map = new HashMap<>();
        map.put("dateStart", dateStart);
        map.put("dateEnd", dateEnd);
        List<OrderSetting> ordersettingList = ordersettingDao.getOrderSettingByMonth(map);

        List<Map> data = ordersettingList.stream()
                .map(e -> getMap(e))
                .collect(Collectors.toList());
        return data;
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        long count = ordersettingDao.findCountByOrderDate(orderSetting.getOrderDate());
        if (count > 0) {
            ordersettingDao.editNumberByOrderDate(orderSetting);
        }else{
            ordersettingDao.add(orderSetting);
        }

    }

    public Map getMap(OrderSetting e) {
        Map<Object, Object> OrderSettingMap = new HashMap();
        OrderSettingMap.put("date", e.getOrderDate().getDate());
        OrderSettingMap.put("number", e.getNumber());
        OrderSettingMap.put("reservations", e.getReservations());
        return OrderSettingMap;
    }

}
