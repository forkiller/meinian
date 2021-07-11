package com.atguigu.service;

import com.atguigu.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author steve
 * @since 2021-07-03 10:53
 */

@SuppressWarnings("all")
public interface OrdersettingService {
    void add(List<OrderSetting> orderSettings);

    List<Map> getOrderSettingByMonth(String date);// date = 2021-07

    void editNumberByDate(OrderSetting orderSetting);
}
