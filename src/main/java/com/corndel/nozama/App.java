package com.corndel.nozama;

import com.corndel.nozama.models.Auth;
import com.corndel.nozama.models.Product;
import com.corndel.nozama.models.Review;
import com.corndel.nozama.models.User;
import com.corndel.nozama.repositories.ProductRepository;
import com.corndel.nozama.repositories.ReviewRepository;
import com.corndel.nozama.repositories.UserRepository;
import controllers.ProductController;
import io.javalin.Javalin;
import io.javalin.http.HttpStatus;

import java.util.List;
import java.util.Map;

public class App {
    private Javalin app;

    public static void main(String[] args) {
        var app = new App().javalinApp();
        app.start(8080);
    }

    public App() {
        app = Javalin.create();
        app.get(
                "/",
                ctx -> {
                    var users = UserRepository.findAll();
                    ctx.json(users);
                });
        app.get(
                "/users/{userId}",
                ctx -> {
                    var id = Integer.parseInt(ctx.pathParam("userId"));
                    var user = UserRepository.findById(id);
                    ctx.status(HttpStatus.IM_A_TEAPOT).json(user);
                });
        app.delete(
                "/users/{userId}",
                ctx -> {
                    var id = Integer.parseInt(ctx.pathParam("userId"));
                    var user = UserRepository.deleteById(id);
                    ctx.status(HttpStatus.IM_A_TEAPOT).json(user);
                });
        app.post(
                "/users",
                ctx -> {
                    User user = ctx.bodyAsClass(User.class);
                    user = UserRepository.create(user);
                    ctx.status(HttpStatus.CREATED).json(user);
                });
        app.post(
                "/users/login",
                ctx -> {
                    Auth auth = ctx.bodyAsClass(Auth.class);
                    User user = UserRepository.logIn(auth);
                    ctx.status(HttpStatus.OK).json(user);
                });

        app.get(
                "/products",
                ProductController::findAll
        );
        app.post("/products",
                ProductController::create);
        app.get(
                "/products/{productId}",
                ProductController::findById
        );
        app.get(
                "/products/category/{categoryId}",
                ProductController::findByCategory
        );
        app.post("/products/{productId}/reviews",
                ctx -> {
                    Review review = ctx.bodyAsClass(Review.class);
                    var id = Integer.parseInt(ctx.pathParam("productId"));
                    review.setId(id);
                    review = ReviewRepository.postReview(review);
                    ctx.status(HttpStatus.CREATED).json(review);
                });
        app.get("/products/{productId}/reviews",
                ctx -> {
                    var id = Integer.parseInt(ctx.pathParam("productId"));
                    List<Review> review = ReviewRepository.getAllByProductId(id);
                    ctx.status(HttpStatus.OK).json(review);
                });

        app.get("/products/{productId}/reviews/average",
                ctx -> {
                    var id = Integer.parseInt(ctx.pathParam("productId"));
                    Map<String, Float> average = ReviewRepository.getAverageRating(id);
                    ctx.status(HttpStatus.OK).json(average);
                });
    }

    public Javalin javalinApp() {
        return app;
    }
}
