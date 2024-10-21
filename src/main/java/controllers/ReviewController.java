package controllers;

import com.corndel.nozama.models.Review;
import com.corndel.nozama.repositories.ReviewRepository;
import com.corndel.nozama.utils.PathParam;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.NotFoundResponse;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ReviewController {

    private static final ReviewRepository reviewRepository = new ReviewRepository();

    // CREATE

    public static void create(Context context) {
        int id = PathParam.getIntegerOrThrow(context, "productId");
        Review review = Review.of(context);
        review.setId(id);
        try {
            review = reviewRepository.create(review);

            if (review == null) throw new NotFoundResponse();

            context.status(HttpStatus.CREATED).json(review);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // READ

    public static void findAllByProductId(Context context) {
        int id = PathParam.getIntegerOrThrow(context, "productId");
        try {
            List<Review> review = reviewRepository.findAllByProductId(id);
            context.status(HttpStatus.OK).json(review);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void findAverageRatingByProductId(Context context) {
        int id = PathParam.getIntegerOrThrow(context, "productId");
        try {
            Map<String, Float> average = reviewRepository.findAverageRatingByProductId(id);
            context.status(HttpStatus.OK).json(average);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // UPDATE

    // DELETE
}
