package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.constant.RedisConstant;
import com.atguigu.enetity.PageResult;
import com.atguigu.enetity.QueryPageBean;
import com.atguigu.enetity.Result;
import com.atguigu.pojo.Setmeal;
import com.atguigu.pojo.TravelGroup;
import com.atguigu.service.SetmealService;
import com.atguigu.service.TravelgroupService;
import com.atguigu.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.UUID;

/**
 * 套餐游
 * @author steve
 * @since 2021-07-02 10:43
 */

@SuppressWarnings("all")
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Reference
    private TravelgroupService travelgroupService;
    @Reference
    private SetmealService setmealService;
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/upload")
    public Result upload(MultipartFile imgFile) {
        try {
            String originalFilename = imgFile.getOriginalFilename();
            System.out.println("originalFilename = " + originalFilename);
            int lastIndexOf = originalFilename.lastIndexOf(".");
            String suffix = originalFilename.substring(lastIndexOf);

            String fileName = UUID.randomUUID().toString() + suffix;
            QiniuUtils.upload2Qiniu(imgFile.getBytes(), fileName);
            Result result = new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, fileName);

            // 将上传图片名称存入 Redis，基于 Redis 的 Set 集合存储
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES, fileName);
            return result;
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
    }

    @RequestMapping("/findAll")
    public Result findAll() {
        List<TravelGroup> travelGroupList = travelgroupService.findAll();
        if (travelGroupList != null && travelGroupList.size() > 0) {
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, travelGroupList);
        }
        return new Result(false, MessageConstant.QUERY_SETMEALLIST_FAIL);
    }

    @RequestMapping("/add")
    public Result add(@RequestParam Integer[] travelgroupIds, @RequestBody Setmeal setmeal) {
        setmealService.add(travelgroupIds, setmeal);
        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = setmealService.findPage(queryPageBean);
        return pageResult;
    }

    @RequestMapping("/findById")
    public Result findById(@RequestParam("id") Integer id) {
        Setmeal setmeal = setmealService.findById(id);
        return new Result(true,MessageConstant.QUERY_SETMEALLIST_SUCCESS,setmeal);
    }

    @RequestMapping("/edit")
    public Result edit(@RequestParam("travelgroupIds") Integer[] travelgroupIds,@RequestBody Setmeal setmeal) {
        Integer id = setmeal.getId();
        setmealService.edit(travelgroupIds,setmeal);
        System.out.println("setmeal = " + setmeal);
        return new Result(true,MessageConstant.EDIT_SERMEAL_SUCCESS);
    }

    @RequestMapping("/deleteById")
    public Result deleteById(@RequestParam Integer id) {
        setmealService.deleteById(id);
        return new Result(true, MessageConstant.DELETE_TRAVELITEM_SUCCESS);
    }
}
