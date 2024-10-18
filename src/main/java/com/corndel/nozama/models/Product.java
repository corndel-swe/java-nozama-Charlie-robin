package com.corndel.nozama.models;

import io.javalin.http.Context;
import io.javalin.validation.ValidationException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Product {

    public static Product of(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getInt("id"));
        product.setName(rs.getString("name"));
        product.setDescription(rs.getString("description"));
        product.setImageURL(rs.getString("imageURL"));
        product.setPrice(rs.getFloat("price"));
        product.setStockQuantity(rs.getInt("stockQuantity"));
        return product;
    }

    public static Product of(Context context) throws ValidationException {
        return context.bodyValidator(Product.class)
                .check((p) -> p.getName() != null && !p.getName().isBlank(), "Name can not be null, or Empty")
                .check((p) -> p.getDescription() != null && !p.getDescription().isBlank(), "Description can not be null, or Empty")
                .check((p) -> p.getPrice() >= 0, "Price must be a positive number")
                .check((p) -> p.getStockQuantity() >= 0, "StockQuantity must be a positive number")
                .check((p) -> p.getImageURL() != null && !p.getImageURL().isBlank(), "ImageURL can not be null, or Empty")
                .get();
    }

    private int id;
    private String name;
    private String description;
    private String imageURL;
    private float price;
    private int stockQuantity;

    public Product() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return "Products{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stockQuantity=" + stockQuantity +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}
