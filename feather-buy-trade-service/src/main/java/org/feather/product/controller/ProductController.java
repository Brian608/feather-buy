package org.feather.product.controller;

import org.feather.common.resp.ApiResult;
import org.feather.product.entity.Category;
import org.feather.product.entity.PageSearch;
import org.feather.product.entity.Product;
import org.feather.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @program: feather-buy
 * @description:
 * @author: 杜雪松(feather)
 * @create: 2020-04-13 20:20
 **/
@RestController
@RequestMapping("product")
public class ProductController {
    @Autowired
    @Qualifier("/ProductServiceImpl")
    private ProductService productService;

    @RequestMapping("/search")
    public ApiResult<List<Product>> searchProduct(@RequestBody PageSearch pageSearch) throws IOException {
        ApiResult<List<Product>> result=new ApiResult<>(200,"查询分类下产品成功");
        List<Product> list=productService.searchProduct(pageSearch.getPageNumber(),
                pageSearch.getPageSize(),pageSearch.getSearchContent());
        result.setData(list);
        return  result;

    }
    @RequestMapping("/category")
    public  ApiResult<List<Category>> listCategory(){
        ApiResult<List<Category>> result=new ApiResult<>(200,"查询分类成功");
        List<Category> list=productService.listCategory();
        result.setData(list);
        return result;
    }
    @RequestMapping("/detail/{id}")
    public ApiResult<Product> productDetail(@PathVariable long id){
        ApiResult <Product> result=new ApiResult<>(200,"获取商品详情成功");
        Product product=productService.productDetail(id);
        result.setData(product);
        return result;
    }
}
