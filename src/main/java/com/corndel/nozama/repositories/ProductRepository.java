package com.corndel.nozama.repositories;

import com.corndel.nozama.DB;
import com.corndel.nozama.models.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository extends Repository<Product> {

    // CREATE

    public Product create(Product product) throws SQLException {
        String query = "INSERT INTO products (name,description,price,stockQuantity,imageURL) VALUES(?,?,?,?,?) RETURNING *";

        try (var connection = DB.getConnection();
             var statement = connection.prepareStatement(query)) {

            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setFloat(3, product.getPrice());
            statement.setInt(4, product.getStockQuantity());
            statement.setString(5, product.getImageURL());

            try (var rs = statement.executeQuery()) {
                return !rs.next() ? null : Product.of(rs);
            }
        }
    }

    // READ

    public List<Product> findAll() throws SQLException {
        String query = "SELECT * FROM products";
        return findAll(query);
    }

    public Product findById(int id) throws SQLException {
        String query = "SELECT * FROM products WHERE id = ?";
        return findByInt(query, id);
    }

    public List<Product> findByCategory(int categoryId) throws SQLException {
        String query = "SELECT * FROM products " +
                "INNER JOIN product_categories ON products.id = product_categories.productId " +
                "INNER JOIN categories ON categories.id = product_categories.categoryId " +
                "WHERE categories.id = ?";
        return findAllByInt(query, categoryId);
    }

    // UPDATE

    // DELETE

    @Override
    public Product resultSetToObject(ResultSet resultSet) throws SQLException {
        return Product.of(resultSet);
    }
}
