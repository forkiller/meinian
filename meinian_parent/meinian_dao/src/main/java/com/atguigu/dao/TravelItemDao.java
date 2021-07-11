package com.atguigu.dao;

import com.atguigu.pojo.TravelItem;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.List;

/**
 * @author steve
 * @since 2021-06-29 11:41
 */
@SuppressWarnings("all")
public interface  TravelItemDao {

    void insert(TravelItem travelItem) throws SQLException;

    Page<TravelItem> findPage(String queryString);

    TravelItem findById(Integer id);

    void edit(TravelItem travelItem);

    void deleteById(Integer id);

    void batchDeleteByOneStatement(@Param("idList") List<Integer> idList);

    List<TravelItem> findAll();

    List<TravelItem> findTravelItemsByTravelgroupId(Integer id);

}
