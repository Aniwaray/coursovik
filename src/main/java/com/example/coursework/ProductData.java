package com.example.coursework;


public class ProductData {
    private String name, description, status;
    private Integer price, stock, category, manufacture, model;

    public ProductData(String name, String description, Integer price, Integer stock, String status, Integer category, Integer manufacture, Integer model) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.status = status;
        this.category = category;
        this.manufacture = manufacture;
        this.model = model;
    }

    public String getName() {
        return this.name;
    }
    public String getDescription() {
        return this.description;
    }
    public Integer getPrice(){return this.price;}
    public Integer getStock() {
        return this.stock;
    }
    public String getStatus(){return this.status;}
    public  Integer getCategory(){return this.category;}
    public  Integer getManufacture(){return this.manufacture;}
    public  Integer getModel(){return this.model;}

    public void setName(String name) {
        this.name = name;
    }
}
