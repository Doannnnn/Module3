package com.example.module3.service;

import com.example.module3.dao.ProductImportDetailDAO;
import com.example.module3.model.ProductImportDetail;

import java.util.List;

public class ProductImportDetailService {
    private ProductImportDetailDAO productImportDetailDAO;
    public ProductImportDetailService(){
        productImportDetailDAO = new ProductImportDetailDAO();
    }

    public List<ProductImportDetail> findById(int id){
        return productImportDetailDAO.findById(id);
    }
}
