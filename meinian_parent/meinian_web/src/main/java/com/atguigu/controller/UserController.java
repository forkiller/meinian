package com.atguigu.controller;

import com.atguigu.constant.MessageConstant;
import com.atguigu.enetity.Result;
import com.atguigu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;

/**
 * @author steve
 * @since 2021-07-08 12:02
 */

@SuppressWarnings("all")
@RestController
@RequestMapping("/user")
public class UserController {

    //@Autowired
    //private UserService userService;

    @RequestMapping("/getUsername")
    public Result getUsername() {
        try {
            User user =
                    (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return new Result(true, MessageConstant.GET_USERNAME_FAIL,user.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
    }
}
