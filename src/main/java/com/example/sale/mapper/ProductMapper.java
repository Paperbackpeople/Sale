package com.example.sale.mapper;

import com.example.sale.entity.Category;
import com.example.sale.entity.Product;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface ProductMapper {
    @Select("SELECT image_url FROM product_images WHERE product_id = #{productId}")
    List<String> getProductImages(Long productId);

    @Select("SELECT c.category_id, c.name " +
            "FROM categories c " +
            "JOIN product_categories pc ON c.category_id = pc.category_id " +
            "WHERE pc.product_id = #{productId}")
    List<Category> getProductCategories(Long productId);

    @Select("SELECT DISTINCT p.product_id, p.name, p.short_description, p.price, p.sale_volume, p.rating " +
            "FROM products p")
    @Results({
            @Result(property = "productId", column = "product_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "shortDescription", column = "short_description"),
            @Result(property = "price", column = "price"),
            @Result(property = "saleVolume", column = "sale_volume"),
            @Result(property = "rating", column = "rating"),
            @Result(property = "images", column = "product_id",
                    javaType = List.class,
                    many = @Many(select = "getProductImages", fetchType = FetchType.LAZY)),
            @Result(property = "categories", column = "product_id",
                    javaType = List.class,
                    many = @Many(select = "getProductCategories", fetchType = FetchType.LAZY))
    })
    List<Product> getAllProducts();
}