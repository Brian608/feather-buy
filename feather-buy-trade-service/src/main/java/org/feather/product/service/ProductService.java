package org.feather.product.service;

import org.feather.product.entity.Category;
import org.feather.product.entity.Product;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    List<Product> searchProduct(int pageNumber, int pageSize, String searchContent) throws IOException;

    List<Category> listCategory();

    Product productDetail(Long id);
}
