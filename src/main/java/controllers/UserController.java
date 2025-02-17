package controllers;

import com.corndel.nozama.models.Auth;
import com.corndel.nozama.models.User;
import com.corndel.nozama.repositories.UserRepository;
import com.corndel.nozama.utils.PathParam;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.NotFoundResponse;

import java.sql.SQLException;

public class UserController {

    private static final UserRepository userRepository = new UserRepository();

    // CREATE

    public static void create(Context context) {
        User user = User.of(context);
        try {
            user = userRepository.create(user);

            if (user == null) throw new NotFoundResponse();

            context.status(HttpStatus.CREATED).json(user);
        } catch (SQLException e) {
            throw new BadRequestResponse(e.getMessage());
        }
    }

    // READ

    public static void findAll(Context context) {
        try {
            context.json(userRepository.findAll());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void findById(Context context) {
        int id = PathParam.getIntegerOrThrow(context, "userId");

        try {
            User user = userRepository.findById(id);

            if (user == null) throw new NotFoundResponse();

            context.status(HttpStatus.OK).json(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void login(Context context) {
        Auth auth = Auth.of(context);
        try {
            User user = userRepository.logIn(auth);

            if (user == null) throw new NotFoundResponse();

            context.status(HttpStatus.OK).json(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // UPDATE

    // DELETE

    public static void delete(Context context) {
        int id = PathParam.getIntegerOrThrow(context, "userId");

        try {
            User user = userRepository.deleteById(id);

            if (user == null) throw new NotFoundResponse();

            context.status(HttpStatus.OK).json(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
