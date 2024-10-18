package controllers;

import com.corndel.nozama.models.Auth;
import com.corndel.nozama.models.User;
import com.corndel.nozama.repositories.UserRepository;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.NotFoundResponse;

import java.sql.SQLException;

public class UserController {

    // CREATE
    public static void create(Context context) {
        User user = User.of(context);
        try {
            user = UserRepository.create(user);

            if (user == null) throw new NotFoundResponse();

            context.status(HttpStatus.CREATED).json(user);
        } catch (SQLException e) {
            throw new BadRequestResponse(e.getMessage());
        }
    }

    // READ

    public static void findAll(Context context) {
        try {
            context.json(UserRepository.findAll());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void findById(Context context) {
        Integer id = context.pathParamAsClass("userId", Integer.class)
                .getOrThrow((m) -> {
                    throw new IllegalArgumentException();
                });
        try {
            User user = UserRepository.findById(id);

            if (user == null) throw new NotFoundResponse();

            context.status(HttpStatus.OK).json(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void login(Context context) {
        Auth auth = Auth.of(context);
        try {
            User user = UserRepository.logIn(auth);

            if (user == null) throw new NotFoundResponse();

            context.status(HttpStatus.OK).json(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // UPDATE

    // DELETE

    public static void delete(Context context) {
        Integer id = context.pathParamAsClass("userId", Integer.class)
                .getOrThrow((m) -> {
                    throw new IllegalArgumentException();
                });

        try {
            User user = UserRepository.deleteById(id);

            if (user == null) throw new NotFoundResponse();

            context.status(HttpStatus.OK).json(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
