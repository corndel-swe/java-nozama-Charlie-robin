package com.corndel.nozama.repositories;

import com.corndel.nozama.DB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Repository<T> {

    // CREATE

    // READ

    public List<T> findAll(String query) throws SQLException {

        try (Connection connection = DB.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);) {

            List<T> all = new ArrayList<>();

            while (resultSet.next()) {
                all.add(resultSetToObject(resultSet));
            }

            return all;
        }
    }

    public T findByInt(String query, int i) throws SQLException {
        try (Connection con = DB.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, i);

            try (ResultSet rs = stmt.executeQuery()) {
                return !rs.next() ? null : resultSetToObject(rs);
            }
        }
    }

    public List<T> findAllByInt(String query, int i) throws SQLException {
        try (Connection con = DB.getConnection();
             PreparedStatement stmt = con.prepareStatement(query);
        ) {
            stmt.setInt(1, i);

            try (ResultSet resultSet = stmt.executeQuery()) {
                List<T> all = new ArrayList<>();

                while (resultSet.next()) {
                    all.add(resultSetToObject(resultSet));
                }

                return all;
            }
        }
    }

    // UPDATE

    // DELETE

    public abstract T resultSetToObject(ResultSet resultSet) throws SQLException;
}
