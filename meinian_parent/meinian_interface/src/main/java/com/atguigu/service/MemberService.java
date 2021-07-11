package com.atguigu.service;

import com.atguigu.pojo.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author steve
 * @since 2021-07-07 10:09
 */

@SuppressWarnings("all")
public interface MemberService {

    void add(Member member);

    Member findMemberByTelephone(String telephone);


    List<Integer> findMemberByMonth(ArrayList<String> list);
}
