package com.corndel.nozama;

import controllers.ProductController;
import controllers.ReviewController;
import controllers.UserController;
import io.javalin.Javalin;

import static io.javalin.apibuilder.ApiBuilder.*;

public class App {
    private final Javalin app;

    public static void main(String[] args) {
        var app = new App().javalinApp();
        app.start(8080);
    }

    public App() {
        app = Javalin.create(config ->
                config.router.apiBuilder(() -> {
                    path("/users", () -> {
                        post("", UserController::create);
                        get("", UserController::findAll);
                        get("/{userId}", UserController::findById);
                        delete("/{userId}", UserController::delete);
                        post("/users/login", UserController::login);
                    });
                    path("/products", () -> {
                        post("", ProductController::create);
                        get("", ProductController::findAll);
                        get("/category/{categoryId}", ProductController::findByCategory);
                        path("/{productId}", () -> {
                            get("", ProductController::findById);
                            post("/reviews", ReviewController::create);
                            get("/reviews", ReviewController::findAllByProductId);
                            get("/reviews/average", ReviewController::findAverageRatingByProductId);
                        });
                    });
                }));

        app.exception(RuntimeException.class, (e, ctx) -> {
            ctx.status(500);
            ctx.result(e.getMessage());
        });
    }

    public Javalin javalinApp() {
        return app;
    }


}
