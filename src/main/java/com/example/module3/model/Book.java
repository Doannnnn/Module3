package com.example.module3.model;

import java.math.BigDecimal;
import java.sql.Date;

public class Book {
    private int id;

    private String title;

    private String description;

    private BigDecimal price;

    private Date publishDate;

    private Author author;
    private boolean deleted = false;


    public Book(String title, String description, BigDecimal price, Date publishDate, Author author) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.publishDate = publishDate;
        this.author = author;
    }

    public Book() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
