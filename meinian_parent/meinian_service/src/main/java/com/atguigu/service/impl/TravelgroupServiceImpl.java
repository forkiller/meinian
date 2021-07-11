package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.TravelGroupDao;
import com.atguigu.enetity.PageResult;
import com.atguigu.enetity.QueryPageBean;
import com.atguigu.pojo.TravelGroup;
import com.atguigu.service.TravelgroupService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author steve
 * @since 2021-06-30 12:32
 */
@SuppressWarnings("all")
@Service(interfaceClass = TravelgroupService.class)
@Transactional
public class TravelgroupServiceImpl implements TravelgroupService {

    @Autowired
    private TravelGroupDao travelGroupDao;

    /**
     * 往 t_group 表添加数据
     *
     * @param travelItemIds
     * @param travelGroup
     */
    @Override
    public void add(Integer[] travelItemIds, TravelGroup travelGroup) {
        // 1 新增跟团游，想t_travelgroup中添加数据，新增后返回新增的id
        travelGroupDao.add(travelGroup);
        // 2 新增跟团游和自由行中间表t_travelgroup_travelitem新增数据(新增几条，由travelItemIds决定)
        System.out.println("travelGroup.getId() = " + travelGroup.getId());
        setTravelGroupAndTravelItem(travelGroup.getId(), travelItemIds);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        // 1：初始化分页操作
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        // 2：使用sql语句进行查询（不必在使用mysql的limit了）
        Page<TravelGroup> page = travelGroupDao.findPage(queryPageBean.getQueryString());
        // 3：封装
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public TravelGroup findById(Integer id) {

        return travelGroupDao.findById(id);
    }

    @Override
    public List<Integer> findTravelItemIdByTravelgroupId(Integer id) {
        List<Integer> list = travelGroupDao.findTravelItemIdByTravelgroupId(id);
        return list;
    }

    @Override
    public void edit(Integer[] travelItemIds, TravelGroup travelGroup) {
        Integer travelGroupId = travelGroup.getId();
        // 1.先更新 t_travelgroup 表数据
        travelGroupDao.edit(travelGroup);

        // 2.先删除 t_travelgroup_travelitem 表中对应 id 的记录，再更新
        travelGroupDao.deleteTravelGroupAndTravelItemByTravelGroupId(travelGroupId);

        // 3.向 t_travelgroup_travelitem 添加数据
        setTravelGroupAndTravelItem(travelGroupId,travelItemIds);
        System.out.println("travelGroupId = " + travelGroupId);

    }

    @Override
    public List<TravelGroup> findAll() {
        List<TravelGroup> travelGroupList =travelGroupDao.findAll();
        return travelGroupList;
    }

    private void setTravelGroupAndTravelItem(Integer travelGroupId, Integer[] travelItemIds) {
        System.out.println("Arrays.toString(travelItemIds) = " + Arrays.toString(travelItemIds));
        if (travelGroupId != null && travelItemIds.length > 0) {
            for (Integer travelItemId : travelItemIds) {
                Map<String, Integer> map = new HashMap();
                map.put("travelGroupId", travelGroupId);
                map.put("travelItemId", travelItemId);
                travelGroupDao.setCheckGroupAndCheckItem(map);
            }
            System.out.println("if setTravelGroupAndTravelItem = ");
        }
        System.out.println("out setTravelGroupAndTravelItem = ");
    }
}
