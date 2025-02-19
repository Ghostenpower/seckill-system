package com.example.seckill_backend.mapper;

import com.example.seckill_backend.model.Order;
import com.example.seckill_backend.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface OrderMapper {
//    List<Order> getOrder(Integer user_id,Integer page_size ,Integer offset);
    List<Order> getOrder(Integer user_id);

    @Select("select count(*) from orders where user_id=#{user_id}")
    Integer getOrderTotal(User user);

    void createOrder(Integer user_id, Integer flash_sale_id , Integer product_id, Integer quantity, BigDecimal total_price);

    void createFlashOrder(Integer user_id, int flash_sale_id, int product_id, int i, BigDecimal total_price);

    @Update("update orders set status = #{status} where order_id = #{order_id}")
    void updateOrder(Order order);

    @Select("select * from orders where order_id = #{order_id}")
    Order getOrderById(Integer orderId);
}
