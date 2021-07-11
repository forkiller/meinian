package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.enetity.PageResult;
import com.atguigu.enetity.QueryPageBean;
import com.atguigu.enetity.Result;
import com.atguigu.pojo.TravelItem;
import com.atguigu.service.TravelItemService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 自由行
 * @author steve
 * @since 2021-06-29 11:33
 */
@SuppressWarnings("all")
@RestController
@RequestMapping("/travelItem")
public class TravelitemController {

    @Reference
    private TravelItemService travelItemService;

    @RequestMapping("/add")
    public Result add(@RequestBody TravelItem travelItem) {
        try {
            travelItemService.add(travelItem);
        } catch (Exception throwables) {
            throwables.printStackTrace();
            return new Result(false, MessageConstant.ADD_TRAVELITEM_FAIL);
        }
        return new Result(true, MessageConstant.ADD_TRAVELITEM_SUCCESS);
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = travelItemService.findPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize(),queryPageBean.getQueryString());
        return pageResult;
    }

    @RequestMapping("/findById")
    public Result findById(Integer id) {
        TravelItem travelItem = travelItemService.findById(id);
        return new Result(true,MessageConstant.DELETE_TRAVELITEM_SUCCESS,travelItem);
    }

    @RequestMapping("/edit")
    public Result edit(@RequestBody TravelItem travelItem) {
        travelItemService.edit(travelItem);
        return new Result(true,MessageConstant.EDIT_TRAVELITEM_SUCCESS);
    }

    @RequestMapping("/deleteById")
    public Result deleteById(Integer id) {
        travelItemService.deleteById(id);
        return new Result(true,MessageConstant.DELETE_TRAVELITEM_SUCCESS);
    }

    @RequestMapping("/batchDelete")
    public Result batchDelete(@RequestBody List<Integer> idList) {
        System.out.println("插入数组=="+ idList);
        idList = idList.stream().distinct().collect(Collectors.toList());
        System.out.println("idList = " + idList);
        try {
            //travelItemService.batchDelete(idList);
            travelItemService.batchDeleteByoneStatement(idList);
            return new Result(true,"批量删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"批量删除失败");
        }
    }


    @RequestMapping("/findAll")
    public Result findAll() {
        List<TravelItem> travelItemList = travelItemService.findAll();
        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,travelItemList);
    }

}
