package controllers;

import com.corndel.nozama.models.Product;
import com.corndel.nozama.repositories.ProductRepository;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.NotFoundResponse;
import io.javalin.validation.ValidationException;

import java.sql.SQLException;
import java.util.List;

public class ProductController {

    // CREATE
    public static void create(Context context) {
        Product product = Product.of(context);

        try {
            product = ProductRepository.create(product);

            if (product == null) throw new NotFoundResponse();

            context.status(HttpStatus.CREATED).json(product);

        } catch (SQLException e) {
            throw new NotFoundResponse();
        }
    }

    // READ

    public static void findAll(Context context) {
        try {
            context.status(HttpStatus.OK).json(ProductRepository.findAll());
        } catch (SQLException e) {
            throw new NotFoundResponse();
        }
    }

    public static void findById(Context context) {
        try {
            Integer id = context.pathParamAsClass("productId", Integer.class).get();

            Product product = ProductRepository.findById(id);

            if (product == null) throw new NotFoundResponse();

            context.status(HttpStatus.OK).json(product);

        } catch (SQLException e) {
            throw new NotFoundResponse();
        }
    }

    public static void findByCategory(Context context) {
        int id = context.pathParamAsClass("categoryId", Integer.class)
                .getOrThrow((m) -> {
                    throw new IllegalArgumentException();
                });

        try {
            List<Product> products = ProductRepository.findByCategory(id);
            context.status(HttpStatus.OK).json(products);
        } catch (SQLException e) {
            throw new NotFoundResponse();
        }
    }

    // UPDATE

    // DELETE
}
