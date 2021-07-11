package com.atguigu.dao;

import com.atguigu.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author steve
 * @since 2021-07-03 11:05
 */

@SuppressWarnings("all")
public interface OrdersettingDao {

    void add(OrderSetting orderSetting);

    void batchAdd(@Param("orderSettings") List<OrderSetting> orderSettings);

    long findCountByOrderDate(Date orderDate);

    void editNumberByOrderDate(OrderSetting orderSetting);

    List<OrderSetting> getOrderSettingByMonth(Map map);

    OrderSetting getOrdersettingorderByDate(Date date);

    void editNumberByDate(OrderSetting orderSetting);


}
