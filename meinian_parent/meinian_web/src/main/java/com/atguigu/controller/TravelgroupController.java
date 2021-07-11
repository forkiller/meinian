package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.enetity.PageResult;
import com.atguigu.enetity.QueryPageBean;
import com.atguigu.enetity.Result;
import com.atguigu.pojo.TravelGroup;
import com.atguigu.service.TravelgroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 跟团游
 * @author steve
 * @since 2021-06-30 12:19
 */
@SuppressWarnings("all")
@RestController
@RequestMapping("/travelgroup")
public class TravelgroupController {

    @Reference
    private TravelgroupService travelgroupService;


    @RequestMapping("/add")
    public Result add(@RequestParam("travelItemIds") Integer[] travelItemIds, @RequestBody TravelGroup travelGroup) {
        travelgroupService.add(travelItemIds, travelGroup);
        return new Result(true, MessageConstant.ADD_TRAVELGROUP_SUCCESS);
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = travelgroupService.findPage(queryPageBean);
        return pageResult;
    }

    @RequestMapping("/findById")
    public Result findById(@RequestParam("id") Integer id ) {
        TravelGroup travelGroup = travelgroupService.findById(id);
        return new Result(true,MessageConstant.QUERY_TRAVELGROUP_SUCCESS,travelGroup);
    }

    @RequestMapping("/findTravelItemIdByTravelgroupId")
    public List<Integer> findTravelItemIdByTravelgroupId(@RequestParam("id") Integer id) {
        List<Integer> list = travelgroupService.findTravelItemIdByTravelgroupId(id);
        return list;
    }

    @RequestMapping("/edit")
    public Result edit(@RequestParam("travelItemIds") Integer[] travelItemIds, @RequestBody TravelGroup travelGroup) {
        travelgroupService.edit(travelItemIds,travelGroup);
        return new Result(true, MessageConstant.EDIT_TRAVELGROUP_SUCCESS);
    }

    /**
     * 用作套餐游编辑查询所有的跟团游列表
     * @return
     */
    @RequestMapping("/findAll")
    public Result findAll() {
        List<TravelGroup> travelGroupList = travelgroupService.findAll();
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, travelGroupList);
    }
}
