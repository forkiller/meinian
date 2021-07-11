package com.atguigu.service;

import com.atguigu.enetity.PageResult;
import com.atguigu.pojo.TravelItem;

import java.sql.SQLException;
import java.util.List;

/**
 * @author steve
 * @since 2021-06-29 11:36
 */
@SuppressWarnings("all")
public interface TravelItemService {

    void add(TravelItem travelItem) throws SQLException;

    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    TravelItem findById(Integer id);

    void edit(TravelItem travelItem);

    void deleteById(Integer id);

    void batchDelete(List<Integer> idList);

    void batchDeleteByoneStatement(List<Integer> idList);

    List<TravelItem> findAll();

}
