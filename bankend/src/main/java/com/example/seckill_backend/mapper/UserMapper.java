package com.example.seckill_backend.mapper;

import com.example.seckill_backend.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    User getUserById(@Param("user_id") Long user_id);

    User getUserByUsername(User user);

    void insertUser(User user);

    void updateUser(User user);

    void deleteUser(Integer user_id);

    String getPassword_hash(User user);

    Boolean isAdmin(User user);

    Object getUserInfo(User user);

    List<User> getUserList(@Param("user") User user, @Param("page_size") Integer page_size, @Param("offset") Integer offset);

    Integer getUserListCount(User user);

    void createUser(User user);

    @Insert("insert into collect(user_id,product_id) values(#{userId},#{productId})")
    void addCollect(Integer userId, Integer productId);

    @Delete("delete from collect where user_id = #{userId} and product_id = #{productId}")
    void deleteCollect(Integer userId, Integer productId);

    void updateInfo(User user);
}