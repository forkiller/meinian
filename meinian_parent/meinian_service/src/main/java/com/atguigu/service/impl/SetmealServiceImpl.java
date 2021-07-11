package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.constant.RedisConstant;
import com.atguigu.dao.SetmealDao;
import com.atguigu.enetity.PageResult;
import com.atguigu.enetity.QueryPageBean;
import com.atguigu.pojo.Setmeal;
import com.atguigu.service.SetmealService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author steve
 * @since 2021-07-02 14:28
 */

@SuppressWarnings("all")
@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;
    @Autowired
    private JedisPool jedisPool;

    @Override
    public void add(Integer[] travelgroupIds, Setmeal setmeal) {
        setmealDao.add(setmeal);
        if (travelgroupIds != null && travelgroupIds.length > 0) {
            setSetmealAndTravelgroup(setmeal.getId(), travelgroupIds);
        }
        //将图片名称保存到Redis
        savePic2Redis(setmeal.getImg());
    }

    /**
     * 将图片名称保存到Redis
     *
     * @param img
     */
    private void savePic2Redis(String img) {
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, img);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());

        Page<Setmeal> page = setmealDao.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public Setmeal findById(Integer id) {
        Setmeal setmeal = setmealDao.findById(id);
        return setmeal;
    }

    @Override
    public void deleteById(Integer id) {
        //1.根据 setmealId 先删除 t_setmeal_travelgroup 表的记录
        setmealDao.deleteSetmealAndtravelgroup(id);
        //2.根据 setmealId 删除 t_setmeal 的记录
        setmealDao.deleteSetmealById(id);
    }

    @Override
    public void edit(Integer[] travelgroupIds, Setmeal setmeal) {
        Integer setmealId = setmeal.getId();
        System.out.println("setmealId = " + setmealId);
        // 1.先更新 t_setmeal 表数据
        setmealDao.edit(setmeal);

        // 2.先删除 t_setmeal_travelgroup 表中对应 setmeal_id 的记录，再更新
        setmealDao.deleteSetmealAndtravelgroupBysetmealId(setmealId);

        // 3.向 t_setmeal_travelgroup 添加数据
        setSetmealAndtravelgroup(setmealId, travelgroupIds);
        System.out.println("setmealId = " + setmealId);
    }

    @Override
    public List<Setmeal> getSetmeal() {

        List<Setmeal> lists = setmealDao.getSetmeal();
        return lists;
    }

    @Override
    public List<Map<String, Object>> findSetMealCount() {
        return setmealDao.findSetMealCount();
    }

    private void setSetmealAndtravelgroup(Integer setmealId, Integer[] travelgroupIds) {
        for (Integer travelgroupId : travelgroupIds) {
            Map map = new HashMap();
            map.put("setmealId", setmealId);
            map.put("travelgroupId", travelgroupId);
            setmealDao.setSetmealAndtravelgroup(map);
        }
    }


    private void setSetmealAndTravelgroup(Integer setmealId, Integer[] travelgroupIds) {
        for (Integer travelgroupId : travelgroupIds) {
            Map<String, Integer> map = new HashMap<>();
            map.put("setmealId", setmealId);
            map.put("travelgroupId", travelgroupId);
            setmealDao.setSetmealAndTravelgroup(map);
        }
    }
}
