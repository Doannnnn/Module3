package com.example.module3.service.dto;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class ProductImportResponse {
    private int id;

    private String code;

    private Date importDate;

    private List<String> products;

    private BigDecimal totalAmount;
    private int quantity;
    private BigDecimal amount;

    public ProductImportResponse(int id, String code, Date importDate, List<String> products, BigDecimal totalAmount, int quantity, BigDecimal amount) {
        this.id = id;
        this.code = code;
        this.importDate = importDate;
        this.products = products;
        this.totalAmount = totalAmount;
        this.quantity = quantity;
        this.amount = amount;
    }
    public ProductImportResponse(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getImportDate() {
        return importDate;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

    public List<String> getProducts() {
        return products;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
