package com.example.sale.mapper;

import com.example.sale.entity.user;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("SELECT COUNT(*) > 0 FROM users WHERE email = #{email}")
    boolean checkEmailExists(String email);

    @Insert("INSERT INTO users (email, created_at) VALUES (#{email}, #{createdAt})")
    void insertUser(user user);

    @Select("SELECT id FROM users WHERE email = #{email}")
    int getUserIdByEmail(String email);

    //查询username是否为空
    @Select("SELECT username FROM users WHERE email = #{email}")
    String getUsernameByEmail(String email);

    //insert username
    @Update("UPDATE users SET username = #{username} WHERE email = #{email}")
    void updateUsername(@Param("username") String username, @Param("email") String email);
}