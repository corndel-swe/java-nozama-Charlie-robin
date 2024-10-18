package com.corndel.nozama.models;

import io.javalin.http.Context;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Review {

    public static Review of(ResultSet rs) throws SQLException {
        Review review = new Review();
        review.setId(rs.getInt("id"));
        review.setProductId(rs.getInt("productId"));
        review.setUserId(rs.getInt("userId"));
        review.setRating(rs.getInt("rating"));
        review.setReviewText(rs.getString("reviewText"));
        review.setReviewDate(rs.getString("reviewDate"));
        return review;
    }

    public static Review of(Context context) {
        return context.bodyValidator(Review.class)
                .check((review) -> review.getRating() >= 1 && review.getRating() <= 5, "Name can not be null, or Empty")
                .check((review) -> review.getReviewText() != null && !review.getReviewText().isBlank(), "Name can not be null, or Empty")
                .get();
    }

    private int id;
    private int productId;
    private int userId;
    private int rating;
    private String reviewText;
    private String reviewDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id='" + id + '\'' +
                ", productId='" + productId + '\'' +
                ", userId='" + userId + '\'' +
                ", rating=" + rating +
                ", reviewText='" + reviewText + '\'' +
                ", reviewDate='" + reviewDate + '\'' +
                '}';
    }
}