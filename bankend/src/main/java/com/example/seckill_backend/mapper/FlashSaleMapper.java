package com.example.seckill_backend.mapper;

import com.example.seckill_backend.model.FlashRecord;
import com.example.seckill_backend.model.FlashSale;
import com.example.seckill_backend.model.FlashSaleView;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FlashSaleMapper {

    FlashSale getFlashSaleById(FlashSale flashSale);

    void initializeFlashSale(FlashSale flashSale);

    void updateFlashSale(FlashSale flashSale);

    List<FlashSale> getFlashSale(@Param("flashSale")FlashSale flashSale,@Param("page_size") Integer page_size,@Param("offset") Integer offset);

    List<FlashSale> getFlashSaleLimit3();

    List<FlashSaleView> getFlashSaleProductLimit3();

    Integer getCountByName(String productName);

    List<FlashSaleView> getByName(String product_name, Integer page_size, Integer offset);

    void decreaseStock(int flashSaleId);

    Integer getCount(FlashSaleView flashSaleView);

    List<FlashSaleView> getFlashSaleView(String product_name,Integer page_size, Integer offset);

    void deleteFlashSale(Integer flashSaleId);

    @Select("select * from flash_record where user_id = #{user_id} and flash_sale_id = #{flashSale_id}")
    FlashRecord checkRecord(Integer user_id, Integer flashSale_id);

    void addRecord(Integer user_id, Integer flashSale_id);

    @Insert("insert into flash_record(user_id,flash_sale_id) values(#{userId},#{flashSaleId})")
    void createFlashRecord(Integer userId, Integer flashSaleId);
}
