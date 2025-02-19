package com.example.seckill_backend.mapper;

import com.example.seckill_backend.model.CollectView;
import com.example.seckill_backend.model.Product;
import com.example.seckill_backend.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProductMapper {

    void createProduct(Product product);

    List<Product> getProduct(@Param("page_size") Integer page_size,@Param("offset") Integer offset);

    List<Product> getProductById(Product product);

    void updateProduct(Product product);

    void deleteProduct(Product product);

    List<Product> getProductByName(@Param("name") String name, @Param("page_size") Integer page_size,@Param("offset") Integer offset);

    Integer getProductCountByName(Product product);

    void descreaseStock(Integer product_id, Integer num);

    Integer getProductCount();

    List<Product> getCollectByName(Integer user_id, String product_name);

    @Select("select * from collect_view where user_id = #{userId} and product_id = #{productId}")
    CollectView searchCollectByUserIdAndProductId(Integer userId, Integer productId);

    Integer getPopularProductCount();

    List<Product> getPopularProduct(Integer pageSize, Integer offset);

    @Select("update product set stock = stock + #{quantity} where product_id = #{productId}")
    void reduction(Integer productId, Integer quantity);
}
