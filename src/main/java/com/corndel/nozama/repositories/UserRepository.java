package com.corndel.nozama.repositories;

import com.corndel.nozama.DB;
import com.corndel.nozama.models.Auth;
import com.corndel.nozama.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository extends Repository<User> {

    // CREATE

    public User create(User user) throws SQLException {
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
                return !rs.next() ? null : User.of(rs);
            }
        }
    }

    // READ

    public List<User> findAll() throws SQLException {
        String query = "SELECT id, username, firstName, lastName, email, avatar FROM users";
        return findAll(query);
    }

    public User findById(int id) throws SQLException {
        String query = "SELECT * FROM users WHERE id = ?";
        return findByInt(query, id);
    }

    public User logIn(Auth auth) throws SQLException {
        String query = "SELECT * FROM users WHERE users.username = ? AND users.password = ?;";
        try (var connection = DB.getConnection();
             var statement = connection.prepareStatement(query)) {

            statement.setString(1, auth.getUsername());
            statement.setString(2, auth.getPassword());

            try (var rs = statement.executeQuery();) {
                return !rs.next() ? null : User.of(rs);
            }
        }
    }

    // UPDATE

    // DELETE

    public User deleteById(int id) throws SQLException {
        var query = "DELETE FROM users WHERE id = ? RETURNING *";

        try (var con = DB.getConnection();
             var stmt = con.prepareStatement(query)) {

            stmt.setInt(1, id);

            try (var rs = stmt.executeQuery()) {
                return !rs.next() ? null : User.of(rs);
            }
        }
    }

    @Override
    public User resultSetToObject(ResultSet resultSet) throws SQLException {
        return User.of(resultSet);
    }
}
