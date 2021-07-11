package com.atguigu.dao;

import com.atguigu.pojo.Order;

import java.util.List;
import java.util.Map;

/**
 * @author steve
 * @since 2021-07-05 20:08
 */

@SuppressWarnings("all")
public interface OrderDao {

    List<Order> getOrdersByCondition(Order order);

    void add(Order order);

    Map findById(Integer id);

    int getTodayVisitsNumber(String today);

    int getTodayOrderNumber(String today);

    int getThisWeekVisitNumber(Map<String, Object> paramWeek);

    int getThisMonthOrderNumber(Map<String, Object> paramMonth);

    int getThisMonthVisitNumber(Map<String, Object> paramMonth);

    List<Map<String, Object>> findHotSetmeal();

    int getThisWeekOrderNumber(Map<String, Object> paramWeek);
}
