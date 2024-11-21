package com.example.sale.service;

import com.example.sale.entity.Category;
import com.example.sale.entity.Product;
import com.example.sale.entity.ProductImage;
import com.example.sale.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductMapper productMapper;

    public List<Product> getAllProducts() {
        return productMapper.getAllProducts();
    }

    public Long checkAndInsertProduct(String name, String imageUrl) {
        // 检查产品是否存在
        Product product = productMapper.getProductByName(name);
        if (product != null) {
            // 产品已存在，返回其 productId
            return product.getProductId();
        } else {
            // 产品不存在，插入新产品
            product = new Product();
            product.setName(name);
            product.setShortDescription("Movie: " + name);
            product.setPrice(new BigDecimal("40.00")); // 电影的价格，您可以根据需要调整
            product.setSaleVolume(0);
            product.setRating(0.0f);

            // 插入产品，获取生成的 productId
            productMapper.insertProduct(product);

            // 插入产品图片
            ProductImage productImage = new ProductImage();
            productImage.setProductId(product.getProductId());
            productImage.setImageUrl(imageUrl); // 电影的图片 URL
            productMapper.insertProductImage(productImage);

            // 返回新插入的 productId
            return product.getProductId();
        }
    }

    // 获取产品详情
    public Product getProductById(Long productId) {
        // 您需要实现根据 productId 获取产品详细信息的方法
        // 包括图片、分类等
        // 这里假设您已经有对应的方法
        // ...
        return null; // 替换为实际实现
    }
}