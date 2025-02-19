package com.example.seckill_backend.service;

import com.example.seckill_backend.mapper.CartMapper;
import com.example.seckill_backend.mapper.OrderMapper;
import com.example.seckill_backend.mapper.ProductMapper;
import com.example.seckill_backend.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ProductMapper productMapper;

    @Transactional(rollbackFor = Exception.class)
    public Result addCart(User user,List<CartView> cartViewList) {
        for (CartView cartView : cartViewList){
            System.out.println(cartView);
            List<CartView> cartViews = cartMapper.getCartByUserIdAndProductId(user.getUser_id(),cartView.getProduct_id());
            System.out.println(cartViews);
            if(!cartViews.isEmpty()){
                cartMapper.addNum(user.getUser_id(),cartView.getProduct_id(),cartView.getProduct_num());
            }else{
                cartMapper.addCart(user.getUser_id(),cartView.getProduct_id(),cartView.getProduct_num());
            }
        }
        return Result.success();
    }

    public Result getCartByName(User user, CartView cartView) {
        return Result.success(cartMapper.getCartByName(user.getUser_id(),cartView.getProduct_name()));
    }

    @Transactional(rollbackFor = Exception.class)
    public Result deleteCartFromList(User user, List<CartView> cartViewList) {
        for (CartView cartView : cartViewList){
            cartMapper.deleteCartFromList(user.getUser_id(),cartView.getProduct_id());
        }
        return Result.success();
    }

    @Transactional(rollbackFor = Exception.class)
    public Result submit(List<Order> orderList) {
        int flag = 0;
        int flag1 = 0;
        for (Order order : orderList){
            Product product = new Product();
            product.setProduct_id(order.getProduct_id());
            List<Product> list = productMapper.getProductById(product);
            if(list.isEmpty()){
                cartMapper.deleteCartFromList(order.getUser_id(),order.getProduct_id());
                flag1 = 1;
            } else if (list.get(0).getStock() < order.getQuantity()) {
                flag = 1;
            } else {
                orderMapper.createOrder(order.getUser_id(),null,order.getProduct_id(),order.getQuantity(),order.getTotal_price());
                cartMapper.deleteCartFromList(order.getUser_id(),order.getProduct_id());
            }
        }
        if (flag == 1 && flag1 == 0){
            return Result.error("部分商品库存不足，其余商品已提交");
        } else if (flag == 0 && flag1 == 1) {
            return Result.error("部分商品不存在，其余商品已提交");
        } else if (flag == 1 && flag1 == 1) {
            return Result.error("部分商品不存在，部分商品库存不足，其余商品已提交");
        }else {
            return Result.success();
        }
    }
}
