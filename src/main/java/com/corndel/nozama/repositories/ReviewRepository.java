package com.corndel.nozama.repositories;

import com.corndel.nozama.DB;
import com.corndel.nozama.models.Review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReviewRepository extends Repository<Review> {

    // CREATE

    public Review create(Review review) throws SQLException {
        var query = "INSERT INTO reviews (productId,userId,rating,reviewText) VALUES (?,?,?,?) RETURNING *";

        try (var con = DB.getConnection(); var statement = con.prepareStatement(query)) {
            statement.setInt(1, review.getProductId());
            statement.setInt(2, review.getUserId());
            statement.setInt(3, review.getRating());
            statement.setString(4, review.getReviewText());

            try (var rs = statement.executeQuery();) {
                return !rs.next() ? null : Review.of(rs);
            }
        }
    }

    // READ

    public List<Review> findAllByProductId(int id) throws SQLException {
        String query = "SELECT * FROM reviews WHERE productId = ?";
        return findAllByInt(query, id);
    }

    public Map<String, Float> findAverageRatingByProductId(int productId) throws SQLException {

        String query = "SELECT AVG(reviews.rating) FROM reviews " +
                "JOIN products ON products.id = reviews.productId " +
                "WHERE reviews.productId = ?";

        Map<String, Float> averageRating = new HashMap<>();

        try (Connection connection = DB.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, productId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    averageRating.put("averageRating", rs.getFloat(1));
                }
            }
        }
        return averageRating;
    }

    // UPDATE
    // DELETE

    @Override
    public Review resultSetToObject(ResultSet resultSet) throws SQLException {
        return Review.of(resultSet);
    }
}
