package com.corndel.nozama.repositories;

import com.corndel.nozama.DB;
import com.corndel.nozama.models.Product;
import com.corndel.nozama.models.Review;
import com.corndel.nozama.models.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
                    reviews.add(Review.ofResultSet(rs));
                }

                return reviews;
            }
        }
    }

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
