package com.corndel.nozama;

import com.corndel.nozama.models.Review;
import com.corndel.nozama.repositories.ReviewRepository;
import controllers.ProductController;
import controllers.ReviewController;
import controllers.UserController;
import io.javalin.Javalin;
import io.javalin.http.HttpStatus;

import java.util.List;
import java.util.Map;

public class App {
    private final Javalin app;

    public static void main(String[] args) {
        var app = new App().javalinApp();
        app.start(8080);
    }

    public App() {
        app = Javalin.create();
        app.get("/users", UserController::findAll);
        app.get("/users/{userId}", UserController::findById);
        app.delete("/users/{userId}", UserController::delete);
        app.post("/users", UserController::create);
        app.post("/users/login", UserController::login);
        app.get("/products", ProductController::findAll);
        app.post("/products", ProductController::create);
        app.get("/products/{productId}", ProductController::findById);
        app.get("/products/category/{categoryId}", ProductController::findByCategory);
        app.post("/products/{productId}/reviews", ReviewController::create);
        app.get("/products/{productId}/reviews", ReviewController::getAllByProductId);
        app.get("/products/{productId}/reviews/average", ReviewController::getAverageRatingByProductId);

        app.exception(RuntimeException.class, (e, ctx) -> {
            ctx.status(500);
            ctx.result(e.getMessage());
        });

    }

    public Javalin javalinApp() {
        return app;
    }



}
