package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.MemberDao;
import com.atguigu.pojo.Member;
import com.atguigu.service.MemberService;
import com.atguigu.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author steve
 * @since 2021-07-07 10:12
 */

@SuppressWarnings("all")
@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;


    @Override
    public void add(Member member) {
        memberDao.add(member);
    }

    @Override
    public Member findMemberByTelephone(String telephone) {
        Member member = memberDao.findMemberByTelephone(telephone);
        return member;
    }

    @Override
    public List<Integer> findMemberByMonth(ArrayList<String> list) {
        // 定义一个容器用来存放每月人数统计
        List<Integer> monthCountList = new ArrayList<>();
        for (String month : list) {
            String LastDayOfMonth = DateUtils.getLastDayOfMonth(month);
            // 迭代过去12个月，每个月之前注册会员的数量，根据注册日期查询
            Integer monthCount = memberDao.findMemberCountBeforeDate(LastDayOfMonth);
            monthCountList.add(monthCount);
        }
        return monthCountList;
    }

}
