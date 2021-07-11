package com.atguigu.controller;

import com.atguigu.constant.MessageConstant;
import com.atguigu.constant.RedisMessageConstant;
import com.atguigu.enetity.Result;
import com.atguigu.utils.SMSUtils;
import com.atguigu.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

/**
 * @author steve
 * @since 2021-07-05 15:50
 */

@SuppressWarnings("all")
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/send4Login")
    public Result send4Login(String telephone) {
        //生成4位数字验证码
        Integer code = ValidateCodeUtils.generateValidateCode(4);
        try {
            // 阿里云 个人短信业务关闭了
            //SMSUtils.sendShortMessage(telephone, code.toString());
            System.out.println("code = " + code);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        // 将生成的验证码存入 redis 中，
        jedisPool.getResource().setex(telephone + RedisMessageConstant.SENDTYPE_ORDER, 5 * 60, code.toString());
        //验证码发送成功
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }

    @RequestMapping("/send4Order")
    public Result send4Order(String telephone) {
        //生成4位数字验证码
        Integer code = ValidateCodeUtils.generateValidateCode(4);
        try {
            // 阿里云 个人短信业务关闭了
            //SMSUtils.sendShortMessage(telephone, code.toString());
            System.out.println("code = " + code);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }

        // 将生成的验证码存入 redis 中，
        jedisPool.getResource().setex(telephone + RedisMessageConstant.SENDTYPE_ORDER, 5 * 60, code.toString());
        //验证码发送成功
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }

}
