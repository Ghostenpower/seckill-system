package com.example.seckill_backend.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartView {
    private Integer user_id;
    @NotBlank(message = "商品id不能为空", groups = {Create.class, Delete.class})
    private Integer product_id;
    @NotBlank(message = "商品名称不能为空", groups = {Get.class})
    private String product_name;
    private BigDecimal product_price;
    private Integer product_num;
    private Integer num;
    private String product_description;

    public interface Create {
    }

    ;

    public interface Update {
    }

    ;

    public interface Delete {
    }

    ;

    public interface Get {
    }

    ;
}
