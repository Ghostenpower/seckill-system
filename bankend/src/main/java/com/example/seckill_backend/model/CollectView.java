package com.example.seckill_backend.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CollectView {
    private Integer user_id;
    @NotNull(message = "商品id不能为空",groups = {Create.class,Delete.class})
    private Integer product_id;
    @NotNull(message = "商品名称不能为空",groups = {Get.class})
    private String product_name;
    private BigDecimal price;
    private Integer stock;
    private String description;

    public interface Get {}
    public interface Create {}
    public interface Delete {}
}
