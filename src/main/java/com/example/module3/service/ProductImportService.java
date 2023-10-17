package com.example.module3.service;

import com.example.module3.dao.ProductImportDAO;
import com.example.module3.dao.ProductImportDetailDAO;
import com.example.module3.model.ProductImport;
import com.example.module3.service.dto.ProductImportListResponse;
import com.example.module3.service.dto.ProductImportResponse;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

public class ProductImportService {
    private ProductImportDAO productImportDAO;
    private ProductImportDetailDAO productImportDetailDAO;

    public ProductImportService() {
        productImportDAO = new ProductImportDAO();
        productImportDetailDAO = new ProductImportDetailDAO();
    }

    public void create(HttpServletRequest req) {
        Date importDate = Date.valueOf(req.getParameter("importDate"));
        String code = req.getParameter("code");
        List<Integer> productIds = Arrays.stream(req.getParameterValues("productIds"))
                .map(Integer::parseInt).toList();

        List<Integer> quantities = Arrays.stream(req.getParameterValues("quantities"))
                .map(Integer::parseInt).toList();
        List<BigDecimal> amounts = Arrays.stream(req.getParameterValues("amounts"))
                .map(BigDecimal::new).toList();

        BigDecimal totalAmount = BigDecimal.ZERO;
        for (int i = 0; i < quantities.size(); i++) {
            // totalAmount += quantity * amount;
            totalAmount = totalAmount.add(amounts.get(i).multiply(BigDecimal.valueOf(quantities.get(i))));
        }
        ProductImport productImport = new ProductImport(0, code,importDate,totalAmount);
        productImport.setId(productImportDAO.create(productImport));

        for (int i = 0; i < quantities.size(); i++) {
            productImportDetailDAO.createImportDetail(productImport.getId(), productIds.get(i), quantities.get(i), amounts.get(i));
        }
    }

    public void edit(HttpServletRequest req){
        int id = Integer.parseInt(req.getParameter("id"));
        productImportDetailDAO.deleteImportDetailById(id);
        Date importDate = Date.valueOf(req.getParameter("importDate"));
        String code = req.getParameter("code");
        List<Integer> productIds = Arrays.stream(req.getParameterValues("productIds"))
                .map(Integer::parseInt).toList();

        List<Integer> quantities = Arrays.stream(req.getParameterValues("quantities"))
                .map(Integer::parseInt).toList();
        List<BigDecimal> amounts = Arrays.stream(req.getParameterValues("amounts"))
                .map(BigDecimal::new).toList();

        BigDecimal totalAmount = BigDecimal.ZERO;
        for (int i = 0; i < quantities.size(); i++) {
            // totalAmount += quantity * amount;
            totalAmount = totalAmount.add(amounts.get(i).multiply(BigDecimal.valueOf(quantities.get(i))));
        }
        ProductImport productImport = new ProductImport(id, code,importDate,totalAmount);
        productImportDAO.update(productImport);

        for (int i = 0; i < quantities.size(); i++) {
            productImportDetailDAO.createImportDetail(productImport.getId(), productIds.get(i), quantities.get(i), amounts.get(i));
        }
    }

    public void delete(HttpServletRequest req){
        int id = Integer.parseInt(req.getParameter("id"));
        productImportDetailDAO.delete(id);
        productImportDAO.delete(id);
    }

    public ProductImport findById(int id){
        return productImportDAO.findById(id);
    }

    public List<ProductImportListResponse> findAll(){
        return productImportDAO.findAll();
    }
}
