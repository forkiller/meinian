package com.atguigu.dao;

import com.atguigu.pojo.TravelGroup;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * @author steve
 * @since 2021-06-30 12:33
 */
@SuppressWarnings("all")
public interface TravelGroupDao {

    void add(TravelGroup travelGroup);

    void setCheckGroupAndCheckItem(Map<String, Integer> map);

    Page<TravelGroup> findPage(String queryString);

    TravelGroup findById(Integer id);

    List<TravelGroup> findTravelGroupsById(Integer id);

    List<Integer> findTravelItemIdByTravelgroupId(Integer setmealId);

    void edit(TravelGroup travelGroup);

    void deleteTravelGroupAndTravelItemByTravelGroupId(Integer travelGroupId);

    List<TravelGroup> findAll();

    List<TravelGroup> findTravelgroupsBySetmealId(Integer setmealId);
}
