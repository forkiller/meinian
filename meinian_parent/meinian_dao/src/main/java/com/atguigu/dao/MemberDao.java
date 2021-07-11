package com.atguigu.dao;

import com.atguigu.pojo.Member;

import java.util.Map;

/**
 * @author steve
 * @since 2021-07-05 20:43
 */

@SuppressWarnings("all")
public interface MemberDao {

    Member findMemberByTelephone(String telephone);

    void add(Member member);


    Integer findMemberCountBeforeDate(String lastDayOfMonth);

    int getTodayNewMember(String today);

    int getThisWeekNewMember(String weekMonday);

    int getTotalMember();

    int getThisMonthNewMember(Map<String, Object> paramMonth);
}
