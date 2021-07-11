package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.constant.RedisMessageConstant;
import com.atguigu.enetity.Result;
import com.atguigu.pojo.Order;
import com.atguigu.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * @author steve
 * @since 2021-07-05 19:29
 */

@SuppressWarnings("all")
//@SuppressWarnings ("unchecked")
@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    private OrderService orderService;

    @Autowired
    private JedisPool jedisPool;


    @RequestMapping("/submitOrder")
    public Result submitOrder(@RequestBody Map<String, Object> map) {

        // 获取前端传入的手机号
        String telephone = (String) map.get("telephone");
        // 获取前端传入的验证码
        String validateCode = (String) map.get("validateCode");

        // 用户输入的验证码和Jedis的验证码比较
        String codeInRedis = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);

        if (validateCode == null || !validateCode.equals(codeInRedis)) {
            // 返回验证码输入错误
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }

        Result result;
        try {
            map.put("orderType", Order.ORDERTYPE_WEIXIN);

            result = orderService.order(map);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
            //return result;
        }

        //预约成功，发送短信通知，短信通知内容可以是“预约时间”，“预约人”，“预约地点”，“预约事项”等信息
        if (result.isFlag()) {
            String orderDate = (String) map.get("orderDate");

            try {
                //SMSUtils.sendShortMessage(telephone, orderDate);
                System.out.println("发送验证码短信给 "+ telephone + " 预约时间"+ orderDate);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    @RequestMapping("/findById")
    public Result findById(@RequestParam Integer id) {

        try {
            Map<Object, Object> map;
            map = orderService.findById(id);
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }

    }
}
