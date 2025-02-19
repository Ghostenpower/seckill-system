package com.example.seckill_backend.service;

import com.example.seckill_backend.mapper.OrderMapper;
import com.example.seckill_backend.mapper.ProductMapper;
import com.example.seckill_backend.mapper.UserMapper;
import com.example.seckill_backend.model.Order;
import com.example.seckill_backend.model.Page;
import com.example.seckill_backend.model.Result;
import com.example.seckill_backend.model.User;
import com.example.seckill_backend.util.PagerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ProductMapper productMapper;

    public Result getOrder(User user, Page<Object> page) {
        try {
            Integer total = orderMapper.getOrderTotal(user);
            if (total == null || total <= 0) {
                total = 0;
            }
            page.setTotal(total);

//            List<Order> orders = orderMapper.getOrder(user.getUser_id(), page.getPage_size(), PagerUtil.getOffset(page.getPage_num(), page.getPage_size()));
            List<Order> orders = orderMapper.getOrder(user.getUser_id());
            page.setItems(orders);

            return Result.success(page);
        } catch (Exception e) {
            // 处理异常，例如记录日志或返回一个默认的 Page 对象
            log.error("获取订单失败", e);
            return Result.error("获取订单失败"+e);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Result pay(Order order, User user) {
        user=userMapper.getUserById(Long.valueOf(user.getUser_id()));
        order=orderMapper.getOrderById(order.getOrder_id());
        if(order==null){
            return Result.error("订单不存在");
        }else if(order.getStatus()==2){
            return Result.error("订单已支付");
        } else if (order.getStatus() == 3) {
            return Result.error("订单已取消");
        }else if (user.getBalance().compareTo(order.getTotal_price()) < 0) {
            return Result.error("余额不足");
        } else {
            user.setBalance(user.getBalance().subtract(order.getTotal_price()));
            userMapper.updateUser(user);
            order.setStatus(2);
            orderMapper.updateOrder(order);
            return Result.success();
        }
    }

    public Result cancel(Order order, User user) {
        order = orderMapper.getOrderById(order.getOrder_id());
        user = userMapper.getUserById(Long.valueOf(user.getUser_id()));
        if(order==null){
            return Result.error("订单不存在");
        }else if (order.getStatus() == 2) {
            user.setBalance(user.getBalance().add(order.getTotal_price()));
            userMapper.updateUser(user);
            order.setStatus(3);
            orderMapper.updateOrder(order);
            productMapper.reduction(order.getProduct_id(), order.getQuantity());
            return Result.success();
        } else if (order.getStatus() == 3) {
            return Result.error("订单已取消");
        } else {
            order.setStatus(3);
            orderMapper.updateOrder(order);
            return Result.success();
        }
    }
}
