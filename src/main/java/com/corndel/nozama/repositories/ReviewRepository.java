package com.corndel.nozama.repositories;

import com.corndel.nozama.DB;
import com.corndel.nozama.models.Review;

import java.sql.SQLException;

public class ReviewRepository {

    public static Review postReview(Review review) throws SQLException {
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

                return Review.ofResultSet(rs);
            }
        }
    }
}
