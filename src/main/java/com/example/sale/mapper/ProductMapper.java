package com.example.sale.mapper;

import com.example.sale.entity.Category;
import com.example.sale.entity.Product;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import com.example.sale.entity.ProductImage;


import java.util.List;

@Mapper
public interface ProductMapper {
    // 检查产品是否存在
    @Select("SELECT * FROM products WHERE name = #{name}")
    Product getProductByName(String name);

    // 插入新产品
    @Insert("INSERT INTO products (name, short_description, price, sale_volume, rating) " +
            "VALUES (#{name}, #{shortDescription}, #{price}, #{saleVolume}, #{rating})")
    @Options(useGeneratedKeys = true, keyProperty = "productId", keyColumn = "product_id")
    void insertProduct(Product product);

    // 插入产品图片
    @Insert("INSERT INTO product_images (image_url, product_id) VALUES (#{imageUrl}, #{productId})")
    void insertProductImage(ProductImage productImage);

    // 获取产品图片
    @Select("SELECT image_url FROM product_images WHERE product_id = #{productId}")
    List<String> getProductImages(Long productId);

    @Select("SELECT c.category_id, c.name " +
            "FROM categories c " +
            "JOIN product_categories pc ON c.category_id = pc.category_id " +
            "WHERE pc.product_id = #{productId}")
    List<Category> getProductCategories(Long productId);


    @Select("SELECT full_description FROM product_details WHERE product_id = #{productId}")
    String getProductFullDescription(Long productId);

    @Select("SELECT DISTINCT p.product_id, p.name, p.short_description, p.price, p.sale_volume, p.rating " +
            "FROM products p")
    @Results({
            @Result(property = "productId", column = "product_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "shortDescription", column = "short_description"),
            @Result(property = "price", column = "price"),
            @Result(property = "saleVolume", column = "sale_volume"),
            @Result(property = "rating", column = "rating"),
            @Result(property = "fullDescription", column = "product_id",
                    javaType = String.class,
                    one = @One(select = "getProductFullDescription", fetchType = FetchType.LAZY)),
            @Result(property = "images", column = "product_id",
                    javaType = List.class,
                    many = @Many(select = "getProductImages", fetchType = FetchType.LAZY)),
            @Result(property = "categories", column = "product_id",
                    javaType = List.class,
                    many = @Many(select = "getProductCategories", fetchType = FetchType.LAZY))
    })
    List<Product> getAllProducts();
}