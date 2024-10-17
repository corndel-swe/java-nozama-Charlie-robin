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

        try {
            Product product = context.bodyValidator(Product.class)
                    .check((p) -> p.getName() != null && !p.getName().isBlank(), "Name can not be null, or Empty")
                    .check((p) -> p.getDescription() != null && !p.getDescription().isBlank(), "Description can not be null, or Empty")
                    .check((p) -> p.getPrice() >= 0, "Price must be a positive number")
                    .check((p) -> p.getStockQuantity() >= 0, "StockQuantity must be a positive number")
                    .check((p) -> p.getImageURL() != null && !p.getImageURL().isBlank(), "ImageURL can not be null, or Empty")
                    .get();

            product = ProductRepository.create(product);

            if (product == null) throw new SQLException();

            context.status(HttpStatus.CREATED).json(product);

        } catch (SQLException e) {
            throw new NotFoundResponse();
        } catch (ValidationException e) {
            throw new BadRequestResponse(e.getMessage());
        }
    }

    // READ

    public static void findAll(Context context) {
        try {
            context.status(HttpStatus.OK).json(ProductRepository.findAll());
        } catch (SQLException e) {
            throw new NotFoundResponse(e.getMessage());
        }
    }

    public static void findById(Context context) {
        try {
            Integer id = context.queryParamAsClass("productId", Integer.class)
                    .getOrThrow((m) -> {
                        throw new IllegalArgumentException();
                    });

            Product product = ProductRepository.findById(id);

            if (product != null) {
                context.status(HttpStatus.OK).json(product);
            } else {
                throw new NotFoundResponse();
            }

        } catch (SQLException e) {
            throw new NotFoundResponse();
        }
    }

    public static void findByCategory(Context context) {
        int id = context.queryParamAsClass("productId", Integer.class)
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
