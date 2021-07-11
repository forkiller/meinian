package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.constant.MessageConstant;
import com.atguigu.dao.MemberDao;
import com.atguigu.dao.OrderDao;
import com.atguigu.dao.OrdersettingDao;
import com.atguigu.enetity.Result;
import com.atguigu.pojo.Member;
import com.atguigu.pojo.Order;
import com.atguigu.pojo.OrderSetting;
import com.atguigu.service.OrderService;
import com.atguigu.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author steve
 * @since 2021-07-05 19:36
 */

@SuppressWarnings("all")
@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrdersettingDao ordersettingDao;

    @Autowired
    private MemberDao memberDao;


    @Override
    public Result order(Map map) throws Exception {
        /**
         * 1.判断预约日期是否有套餐
         * 2.预约日期的预约人数是否已满
         * 3.用户是否为会员，若是直接下单，否则就注册用户然后下单
         * 4.同个用户当天不能预约同一个套餐
         * 5.预约成功，更新预约表 t_ordersetting +1（暂不考虑多并发情况）
         */

        // 1.判断预约日期是否有套餐
        String orderDate = (String) map.get("orderDate");
        Date date = DateUtils.parseString2Date(orderDate);

        OrderSetting ordersetting = ordersettingDao.getOrdersettingorderByDate(date);
        if (ordersetting == null) {
            // 预约日没有旅游套餐
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        } else {
            // 2.预约日期的预约人数是否已满
            int number = ordersetting.getNumber();
            int reservations = ordersetting.getReservations();

            if (reservations >= number) {
                return new Result(false, MessageConstant.ORDER_FULL);
            }
            // 根据手机号在 t_member表 查询是否存在会员
            String telephone = (String) map.get("telephone");
            Member member = memberDao.findMemberByTelephone(telephone);

            if (member != null) {
                // 查询到会员
                Integer memberId = member.getId();
                int setmealId = Integer.parseInt((String) map.get("setmealId"));

                Order order = new Order(memberId, date, null, null, setmealId);
                List<Order> list = orderDao.getOrdersByCondition(order);

                // 同一个用户已经预约过同一个套餐了
                if (list != null && list.size() > 0) {
                    return new Result(false, MessageConstant.HAS_ORDERED);
                }
            } else {
                // 不是会员则注册下单
                member = new Member();
                member.setName((String) map.get("name"));
                member.setSex((String) map.get("sex"));
                member.setIdCard((String) map.get("idCard"));
                member.setPhoneNumber((String) map.get("telephone"));
                // 会员注册时间
                member.setRegTime(new Date());
                memberDao.add(member);
                System.out.println("memberId = " + member.getId());
            }

            // 预约人数未满--> 向订单表中插入数据，预约人数表更新
            Integer memberId = member.getId();

            int setmealId = Integer.parseInt((String) map.get("setmealId"));
            String orderType = (String) map.get("orderType");

            Order order = new Order();
            order.setMemberId(memberId);
            order.setOrderDate(date);
            order.setOrderType(orderType);
            order.setOrderStatus(Order.ORDERSTATUS_NO);
            order.setSetmealId(setmealId);

            orderDao.add(order);
            System.out.println(order.getId());
            ordersetting.setReservations(ordersetting.getReservations() + 1);
            ordersettingDao.editNumberByOrderDate(ordersetting);
            return new Result(true, MessageConstant.ORDER_SUCCESS, order);
        }

    }

    @Override
    public Map findById(Integer id) throws Exception {
        Map map = orderDao.findById(id);
        if(map != null){
            //处理日期格式
            Date orderDate = (Date) map.get("orderDate");
            map.put("orderDate",DateUtils.parseDate2String(orderDate));
            return map;
        }
        return map;
    }
}
