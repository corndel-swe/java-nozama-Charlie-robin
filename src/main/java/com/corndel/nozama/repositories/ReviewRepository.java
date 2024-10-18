package com.corndel.nozama.repositories;

import com.corndel.nozama.DB;
import com.corndel.nozama.models.Review;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReviewRepository {

    public static List<Review> getAllByProductId(int id) throws SQLException {
        var query = "SELECT * FROM reviews WHERE productId = ?";

        try (var con = DB.getConnection();
             var stmt = con.prepareStatement(query);
        ) {
            stmt.setInt(1, id);

            try (var rs = stmt.executeQuery()) {
                var reviews = new ArrayList<Review>();
                while (rs.next()) {
                    reviews.add(Review.of(rs));
                }

                return reviews;
            }
        }
    }

    public static Review create(Review review) throws SQLException {
        var query = "INSERT INTO reviews (productId,userId,rating,reviewText) VALUES (?,?,?,?) RETURNING *";

        try (var con = DB.getConnection(); var statement = con.prepareStatement(query)) {
            statement.setInt(1, review.getProductId());
            statement.setInt(2, review.getUserId());
            statement.setInt(3, review.getRating());
            statement.setString(4, review.getReviewText());

            try (var rs = statement.executeQuery();) {
                while (!rs.next()) {
                    return null;
                }

                return Review.of(rs);
            }
        }
    }

    public static Map<String, Float> getAverageRatingByProductId(int productId) throws SQLException {

        var query = "SELECT AVG(reviews.rating) FROM reviews " +
                "JOIN products ON products.id = reviews.productId " +
                "WHERE reviews.productId = ?";

        Map<String, Float> averageRating = new HashMap<>();
        try (var connection = DB.getConnection();
             var stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, productId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    averageRating.put("averageRating", rs.getFloat(1));
                }
            }
        }
        return averageRating;
    }


}
