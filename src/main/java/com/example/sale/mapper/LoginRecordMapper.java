package com.example.sale.mapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import java.util.List;
import com.example.sale.entity.LoginRecord;

@Mapper
public interface LoginRecordMapper {
    @Insert("INSERT INTO login_records (user_id, login_time) VALUES (#{userId}, #{loginTime})")
    void insertLoginRecord(LoginRecord loginRecord);

    @Select("SELECT * FROM login_records WHERE user_id = #{userId}")
    List<LoginRecord> getLoginRecordsByUserId(int userId);
}
