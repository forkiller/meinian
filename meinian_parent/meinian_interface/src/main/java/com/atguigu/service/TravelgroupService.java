package com.atguigu.service;

import com.atguigu.enetity.PageResult;
import com.atguigu.enetity.QueryPageBean;
import com.atguigu.pojo.TravelGroup;

import java.util.List;

/**
 * @author steve
 * @since 2021-06-30 12:26
 */
@SuppressWarnings("all")
public interface TravelgroupService {

    void add(Integer[] travelItemIds, TravelGroup travelGroup);

    PageResult findPage(QueryPageBean queryPageBean);

    TravelGroup findById(Integer id);

    List<Integer> findTravelItemIdByTravelgroupId(Integer id);

    void edit(Integer[] travelItemIds, TravelGroup travelGroup);

    List<TravelGroup> findAll();
}
