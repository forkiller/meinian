package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.MemberDao;
import com.atguigu.dao.OrderDao;
import com.atguigu.service.ReportService;
import com.atguigu.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author steve
 * @since 2021-07-08 19:28
 */

@SuppressWarnings("all")
@Service(interfaceClass = ReportService.class)
public class ReportServiceImpl implements ReportService {

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrderDao orderDao;

    @Override
    public Map getBusinessReportData() {
        Map<String,Object> map = null;
        try {
            // 1.当前日期
            String today = DateUtils.parseDate2String(DateUtils.getToday());
            // 2：本周（周一）
            String weekMonday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
            // 3：本周（周日）
            String weekSunday = DateUtils.parseDate2String(DateUtils.getSundayOfThisWeek());
            // 4：本月（1号）
            String monthFirst = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());
            // 5：本月（31号）
            String monthLast = DateUtils.parseDate2String(DateUtils.getLastDay4ThisMonth());
            // 新增会员数
            int todayNewMember = memberDao.getTodayNewMember(today);
            // 总会员数
            int totalMember = memberDao.getTotalMember();
            // 本周新增会员数
            int thisWeekNewMember = memberDao.getThisWeekNewMember(weekMonday);

            // 今日预约数
            int todayOrderNumber = orderDao.getTodayOrderNumber(today);
            // 今日出游数
            int todayVisitsNumber = orderDao.getTodayVisitsNumber(today);

            // 本周参数
            Map<String, Object> paramWeek = new HashMap<>();
            paramWeek.put("begin", weekMonday);
            paramWeek.put("end", weekSunday);
            // 本周预约数
            int thisWeekOrderNumber = orderDao.getThisWeekOrderNumber(paramWeek);
            // 本周出游数
            int thisWeekVisitsNumber = orderDao.getThisWeekVisitNumber(paramWeek);

            // 本月参数
            Map<String, Object> paramMonth = new HashMap<>();
            paramMonth.put("begin", monthFirst);
            paramMonth.put("end", monthLast);
            // 本月新增会员数
            int thisMonthNewMember = memberDao.getThisMonthNewMember(paramMonth);
            // 本月预约数
            int thisMonthOrderNumber = orderDao.getThisMonthOrderNumber(paramMonth);
            // 本月出游数
            int thisMonthVisitsNumber = orderDao.getThisMonthVisitNumber(paramMonth);

            // 热门套餐
            List<Map<String,Object>> hotSetmeal = orderDao.findHotSetmeal();

            map = new HashMap<String,Object>();
            /*
             *      reportDate（当前时间）--String
             *      todayNewMember（今日新增会员数） -> number
             *      totalMember（总会员数） -> number
             *      thisWeekNewMember（本周新增会员数） -> number
             *      thisMonthNewMember（本月新增会员数） -> number
             *      todayOrderNumber（今日预约数） -> number
             *      todayVisitsNumber（今日出游数） -> number
             *      thisWeekOrderNumber（本周预约数） -> number
             *      thisWeekVisitsNumber（本周出游数） -> number
             *      thisMonthOrderNumber（本月预约数） -> number
             *      thisMonthVisitsNumber（本月出游数） -> number
             *      hotSetmeal（热门套餐（取前4）） -> List<Setmeal>
             **/
            map.put("reportDate",today);
            map.put("todayNewMember",todayNewMember);
            map.put("totalMember",totalMember);
            map.put("thisWeekNewMember",thisWeekNewMember);
            map.put("thisMonthNewMember",thisMonthNewMember);
            map.put("todayOrderNumber",todayOrderNumber);
            map.put("todayVisitsNumber",todayVisitsNumber);
            map.put("thisWeekOrderNumber",thisWeekOrderNumber);
            map.put("thisWeekVisitsNumber",thisWeekVisitsNumber);
            map.put("thisMonthOrderNumber",thisMonthOrderNumber);
            map.put("thisMonthVisitsNumber",thisMonthVisitsNumber);
            map.put("hotSetmeal",hotSetmeal);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
