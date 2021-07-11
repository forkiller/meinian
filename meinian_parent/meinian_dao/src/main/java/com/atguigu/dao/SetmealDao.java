package com.atguigu.dao;

import com.atguigu.pojo.Setmeal;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author steve
 * @since 2021-07-02 14:30
 */

@SuppressWarnings("all")
public interface SetmealDao {

    void add(Setmeal setmeal);

    void setSetmealAndTravelgroup(Map<String, Integer> map);

    Page<Setmeal> findPage(String queryString);

    Setmeal findById(Integer id);

    void deleteSetmealAndtravelgroup(Integer id);

    void deleteSetmealById(Integer id);

    //void edit(@Param("setmeal") Setmeal setmeal);
    void edit(Setmeal setmeal);

    void setSetmealAndtravelgroup(Map map);

    void deleteSetmealAndtravelgroupBysetmealId(Integer setmealId);


    List<Setmeal> getSetmeal();

    // 返回[{setmealName1:count1},{setmealName2:count2}...]
    List<Map<String, Object>> findSetMealCount();
}
