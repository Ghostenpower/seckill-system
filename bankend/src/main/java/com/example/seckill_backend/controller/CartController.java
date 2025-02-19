package com.example.seckill_backend.controller;

import com.example.seckill_backend.model.*;
import com.example.seckill_backend.service.CartService;
import com.example.seckill_backend.util.MultiRequestBody;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/test")
    public Result test(@RequestBody @Validated(CartView.Delete.class) List<CartView> cartViewList){
        System.out.println(cartViewList.get(0).getProduct_name());
        return Result.success();
    }

    @PostMapping("/add")
    public Result addCart(@RequestBody  List<CartView> cartViewList,HttpServletRequest request){
        User user = (User) request.getAttribute("user");
        return cartService.addCart(user,cartViewList);
    }

    @PostMapping("/getCartByName")
    public Result getCartByName(@MultiRequestBody @Validated(CartView.Get.class) CartView cartView, HttpServletRequest request){
        User user = (User) request.getAttribute("user");
        return cartService.getCartByName(user,cartView);
    }

    @PostMapping("/deleteCartFromList")
    public Result deleteCartList(@RequestBody List<CartView> cartViewList, HttpServletRequest request){
        User user = (User) request.getAttribute("user");
        return cartService.deleteCartFromList(user,cartViewList);
    }

    @PostMapping("/submit")
    public Result submit(@RequestBody List<Order> orderList){
        return cartService.submit(orderList);
    }
}
