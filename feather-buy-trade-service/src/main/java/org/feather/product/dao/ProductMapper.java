package org.feather.product.dao;

import org.apache.ibatis.annotations.Mapper;
import org.feather.product.entity.Product;

import java.util.List;

@Mapper
public interface ProductMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> selectByCategory(Long categoryId);

    Product selectByPrimaryKeyWithSku(Long id);
}