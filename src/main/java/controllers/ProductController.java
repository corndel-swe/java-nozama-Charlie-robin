package controllers;

import com.corndel.nozama.models.Product;
import com.corndel.nozama.repositories.ProductRepository;
import com.corndel.nozama.utils.PathParam;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.NotFoundResponse;
import io.javalin.validation.ValidationException;

import java.sql.SQLException;
import java.util.List;

public class ProductController {

    private static final ProductRepository productRepository = new ProductRepository();

    // CREATE

    public static void create(Context context) {
        Product product = Product.of(context);

        try {
            product = productRepository.create(product);

            if (product == null) throw new NotFoundResponse();

            context.status(HttpStatus.CREATED).json(product);

        } catch (SQLException e) {
            throw new NotFoundResponse(e.getMessage());
        }
    }

    // READ

    public static void findAll(Context context) {
        try {
            context.status(HttpStatus.OK).json(productRepository.findAll());
        } catch (SQLException e) {
            throw new NotFoundResponse(e.getMessage());
        }
    }

    public static void findById(Context context) {
        int id = PathParam.getIntegerOrThrow(context, "productId");

        try {
            Product product = productRepository.findById(id);

            if (product == null) throw new NotFoundResponse();

            context.status(HttpStatus.OK).json(product);

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void findByCategory(Context context) {
        int id = PathParam.getIntegerOrThrow(context, "categoryId");

        try {
            List<Product> products = productRepository.findByCategory(id);
            context.status(HttpStatus.OK).json(products);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // UPDATE

    // DELETE
}
