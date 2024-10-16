package com.corndel.nozama.repositories;

import com.corndel.nozama.DB;
import com.corndel.nozama.models.Auth;
import com.corndel.nozama.models.Product;
import com.corndel.nozama.models.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    public static List<User> findAll() throws SQLException {
        var query = "SELECT id, username, firstName, lastName, email, avatar FROM users";

        try (var con = DB.getConnection();
             var stmt = con.createStatement();
             var rs = stmt.executeQuery(query);) {

            var users = new ArrayList<User>();
            while (rs.next()) {
                users.add(User.ofResultSet(rs));
            }

            return users;
        }
    }

    public static User findById(int id) throws SQLException {
        var query = "SELECT username, firstName, lastName, email, avatar FROM users WHERE id = ?";

        try (var con = DB.getConnection();
             var stmt = con.prepareStatement(query)) {

            stmt.setInt(1, id);

            try (var rs = stmt.executeQuery()) {

                while (!rs.next()) {
                    return null;
                }

                return User.ofResultSet(rs);

            }
        }
    }

    public static User create(User user) throws SQLException {
        String query = "INSERT INTO users (username,firstName,lastName,email,avatar,password) VALUES(?,?,?,?,?,?) RETURNING *";

        try (var connection = DB.getConnection();
             var statement = connection.prepareStatement(query)) {

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getAvatar());
            statement.setString(6, user.getPassword());

            try (var rs = statement.executeQuery();) {
                while (!rs.next()) {
                    return null;
                }

                return User.ofResultSet(rs);
            }
        }
    }

    public static User logIn(Auth auth) throws SQLException {
        String query = "SELECT * FROM users WHERE users.username = ? AND users.password = ?;";
        try (var connection = DB.getConnection();
             var statement = connection.prepareStatement(query)) {

            statement.setString(1, auth.getUserName());
            statement.setString(2, auth.getPassword());


            try (var rs = statement.executeQuery();) {
                while (!rs.next()) {
                    return null;
                }

                return User.ofResultSet(rs);
            }
        }
    }
}
