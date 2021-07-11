package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.TravelItemDao;
import com.atguigu.enetity.PageResult;
import com.atguigu.pojo.TravelItem;
import com.atguigu.service.TravelItemService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

/**
 * @author steve
 * @since 2021-06-29 11:38
 */
@SuppressWarnings("all")
@Service(interfaceClass = TravelItemService.class)
@Transactional
public class TravelItemServiceImpl implements TravelItemService{

    @Autowired
    private TravelItemDao travelItemDao;

    @Override
    public void add(TravelItem travelItem) throws SQLException {
        travelItemDao.insert(travelItem);
    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        // 不使用分页插件PageHelper
        // 至少写2条sql语句完成查询
        // 第1条，select count(*) from t_travelitem，查询的结果封装到PageResult中的total
        // 第2条，select * from t_travelitem where NAME = '001' OR CODE = '001' limit ?,?  （0,10）（10,10）(（currentPage-1)*pageSize,pageSize）
        // 使用分页插件PageHelper（简化上面的操作）
        // 1：初始化分页操作
        PageHelper.startPage(currentPage,pageSize);
        // 2：使用sql语句进行查询（不必在使用mysql的limit了）
        Page<TravelItem> page = travelItemDao.findPage(queryString);
        // 3：封装
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public TravelItem findById(Integer id) {

        return travelItemDao.findById( id);
    }

    @Override
    public void edit(TravelItem travelItem) {
        travelItemDao.edit(travelItem);
    }

    @Override
    public void deleteById(Integer id) {
        travelItemDao.deleteById(id);
    }

    @Override
    public void batchDelete(List<Integer> idList) {
        for (Integer id : idList) {
            //System.out.println("id = " + id);
            travelItemDao.deleteById(id);
        }
    }

    @Override
    public void batchDeleteByoneStatement(List<Integer> idList) {
            travelItemDao.batchDeleteByOneStatement(idList);
    }

    @Override
    public List<TravelItem> findAll() {

        return travelItemDao.findAll();
    }


}
