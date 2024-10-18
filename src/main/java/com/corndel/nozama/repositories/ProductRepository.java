package com.corndel.nozama.repositories;

import com.corndel.nozama.DB;
import com.corndel.nozama.models.Product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    public static List<Product> findAll() throws SQLException {
        var query = "SELECT * FROM products";

        try (var con = DB.getConnection();
             var stmt = con.createStatement();
             var rs = stmt.executeQuery(query);) {

            var products = new ArrayList<Product>();
            while (rs.next()) {
                products.add(Product.of(rs));
            }

            return products;
        }
    }

    public static Product findById(int id) throws SQLException {
        var query = "SELECT * FROM products WHERE id = ?";

        try (var con = DB.getConnection();
             var stmt = con.prepareStatement(query);
        ) {
            stmt.setInt(1, id);

            try (var rs = stmt.executeQuery()) {

                while (!rs.next()) {
                    return null;
                }

                return Product.of(rs);
            }
        }
    }

    public static List<Product> findByCategory(int categoryId) throws SQLException {
        String query = "SELECT * FROM products " +
                "INNER JOIN product_categories ON products.id = product_categories.productId " +
                "INNER JOIN categories ON categories.id = product_categories.categoryId " +
                "WHERE categories.id = ?";

        try (var con = DB.getConnection();
             var stmt = con.prepareStatement(query);
        ) {
            stmt.setInt(1, categoryId);

            try (var rs = stmt.executeQuery()) {
                var products = new ArrayList<Product>();
                while (rs.next()) {
                    products.add(Product.of(rs));
                }

                return products;
            }
        }
    }


    public static Product create(Product product) throws SQLException {
        String query = "INSERT INTO products (name,description,price,stockQuantity,imageURL) VALUES(?,?,?,?,?) RETURNING *";

        try (var connection = DB.getConnection();
             var statement = connection.prepareStatement(query)) {

            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setFloat(3, product.getPrice());
            statement.setInt(4, product.getStockQuantity());
            statement.setString(5, product.getImageURL());

            try (var rs = statement.executeQuery();) {
                while (!rs.next()) {
                    return null;
                }

                return Product.of(rs);
            }
        }
    }


    public static void main(String[] args) {

        try {
//
//            System.out.println(findAll());
//            Product product = findById("72");
//
//            assert product != null;
//            System.out.println(create(product));

            System.out.println(findByCategory(1));
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
