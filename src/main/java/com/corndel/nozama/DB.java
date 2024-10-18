package com.corndel.nozama;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
  public static final String dbUrl = Dotenv.load().get("DB_URL");

  public static Connection getConnection() throws SQLException {
      assert dbUrl != null;
      return DriverManager.getConnection(dbUrl);
  }
}
