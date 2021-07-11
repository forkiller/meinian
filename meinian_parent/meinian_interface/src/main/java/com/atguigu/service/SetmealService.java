package com.atguigu.service;

import com.atguigu.enetity.PageResult;
import com.atguigu.enetity.QueryPageBean;
import com.atguigu.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * @author steve
 * @since 2021-07-02 14:28
 */

@SuppressWarnings("all")
public interface SetmealService {

    void add(Integer[] travelgroupIds, Setmeal setmeal);

    PageResult findPage(QueryPageBean queryPageBean);

    Setmeal findById(Integer id);

    void deleteById(Integer id);

    void edit(Integer[] travelgroupIds, Setmeal setmeal);

    List<Setmeal> getSetmeal();

    List<Map<String, Object>> findSetMealCount();
}
