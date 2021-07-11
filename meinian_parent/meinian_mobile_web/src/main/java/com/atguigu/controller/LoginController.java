package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.constant.RedisMessageConstant;
import com.atguigu.enetity.Result;
import com.atguigu.pojo.Member;
import com.atguigu.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * @author steve
 * @since 2021-07-07 10:08
 */

@SuppressWarnings("all")
@RestController
@RequestMapping("/login")
public class LoginController {

    @Reference
    private MemberService memberService;

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/check")
    public Result check(HttpServletResponse response, @RequestBody Map map) {
        String telephone = (String) map.get("telephone");
        String code = (String) map.get("validateCode");

        String codeInRedis = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_LOGIN);

        if (codeInRedis == null || !codeInRedis.equals(code)) {
            // 根据电话查找会员记录
            Member member = memberService.findMemberByTelephone(telephone);
            if (member == null) {
                // 未注册，则直接注册
                member = new Member();
                member.setPhoneNumber(telephone);
                member.setRegTime(new Date());

                memberService.add(member);

            }
            // 已注册，保存cookie
            Cookie cookie = new Cookie("login_member_telephone", telephone);
            // 设置 cookie 的作用范围
            cookie.setPath("/");
            // 设置 cookie 的过期时间
            cookie.setMaxAge(24 * 60 * 60);
            // 将 cookie 添加到 response
            response.addCookie(cookie);

        }

        return new Result(true, MessageConstant.LOGIN_SUCCESS);
    }
}
