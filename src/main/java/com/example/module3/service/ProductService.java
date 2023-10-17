package com.example.module3.service;


import com.example.module3.dao.ProductDAO;
import com.example.module3.model.Product;
import com.example.module3.service.dto.Page;

import java.util.List;

public class ProductService {
    private final ProductDAO productDAO;

    public ProductService() {
        productDAO = new ProductDAO();
    }


    public void create(Product product){
        productDAO.create(product);
    }

    public Page<Product> getProducts(int page, boolean isShowRestore, String search){
        return productDAO.findAll(page, isShowRestore, search);
    }

    public List<Product> findAll(){
        return productDAO.findAll();
    }

    public Product findById(int id){
        return productDAO.findById(id);
    }

    public void update(Product product, int id){
        product.setId(id);
        productDAO.update(product);
    }

    public void restore(String[] ids){
        for (String id : ids) {
            productDAO.restore(Integer.parseInt(id));
        }
    }

    public void delete(int id){
        productDAO.delete(id);
    }
}
