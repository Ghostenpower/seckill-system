package com.example.seckill_backend.mapper;

import com.example.seckill_backend.model.CartView;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CartMapper {
    public void addCart(Integer user_id, Integer product_id, Integer quantity);

    @Select("select * from cart where user_id = #{user_id} and product_id = #{product_id}")
    List<CartView> getCartByUserIdAndProductId(Integer user_id, Integer product_id);

    List<CartView> getCartByName(Integer user_id, String product_name);

    @Select("delete from cart where user_id = #{user_id} and product_id = #{product_id}")
    void deleteCartFromList(Integer user_id, Integer product_id);

    void addNum(Integer user_id,Integer product_id,Integer quantity);
}
