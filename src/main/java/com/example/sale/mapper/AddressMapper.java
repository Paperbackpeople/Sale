package com.example.sale.mapper;

import com.example.sale.entity.Address;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AddressMapper {

    @Insert("INSERT INTO addresses (user_id, address_line, province, city, state, tel, is_primary, created_at, updated_at) " +
            "VALUES (#{userId}, #{addressLine}, #{province}, #{city}, #{state}, #{tel}, #{isPrimary}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "addressId")
    void insertAddress(Address address);

    @Update("UPDATE addresses SET is_primary = 0 WHERE user_id = #{userId}")
    void clearDefaultAddress(Long userId);

    @Update({
            "<script>",
            "UPDATE addresses",
            "SET is_primary = CASE WHEN address_id = #{addressId} THEN 1 ELSE 0 END",
            "WHERE user_id = (SELECT user_id FROM addresses WHERE address_id = #{addressId})",
            "</script>"
    })
    void setDefaultAddress(Long addressId);

    @Select("SELECT * FROM addresses WHERE user_id = #{userId} AND is_primary = 1 LIMIT 1")
    @Results({
            @Result(property = "addressId", column = "address_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "addressLine", column = "address_line"),
            @Result(property = "province", column = "province"),
            @Result(property = "city", column = "city"),
            @Result(property = "state", column = "state"),
            @Result(property = "isPrimary", column = "is_primary"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at"),
            @Result(property = "tel", column = "tel")
    })
    Address getDefaultAddress(Long userId);

    @Select("SELECT * FROM addresses WHERE user_id = #{userId} ORDER BY is_primary DESC, updated_at DESC")
    @Results({
            @Result(property = "addressId", column = "address_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "addressLine", column = "address_line"),
            @Result(property = "province", column = "province"),
            @Result(property = "city", column = "city"),
            @Result(property = "state", column = "state"),
            @Result(property = "isPrimary", column = "is_primary"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at"),
            @Result(property = "tel", column = "tel")
    })
    List<Address> getAllAddresses(Long userId);

    @Select("SELECT id FROM users WHERE email = #{email}")
    Long getUserIdByEmail(String email);

    @Select("SELECT * FROM addresses WHERE address_id = #{addressId}")
    @Results({
            @Result(property = "addressId", column = "address_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "addressLine", column = "address_line"),
            @Result(property = "province", column = "province"),
            @Result(property = "city", column = "city"),
            @Result(property = "state", column = "state"),
            @Result(property = "isPrimary", column = "is_primary"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at"),
            @Result(property = "tel", column = "tel")
    })
    Address getAddressById(Long addressId);

    @Update("UPDATE addresses SET address_line = #{addressLine}, province = #{province}, city = #{city}, state = #{state}, tel = #{tel}, is_primary = #{isPrimary}, updated_at = NOW() WHERE address_id = #{addressId} AND user_id = #{userId}")
    void updateAddress(Address address);



}