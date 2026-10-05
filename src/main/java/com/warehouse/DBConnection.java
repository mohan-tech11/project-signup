package com.warehouse;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/warehouse_fulfillment";

    private static final String USER = "root";

    private static final String PASSWORD = "";

    public static Connection getConnection() {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );

            System.out.println("DATABASE CONNECTION SUCCESSFUL!");

            return connection;

        } catch (Exception e) {

            System.out.println("DATABASE CONNECTION FAILED!");

            e.printStackTrace();

            throw new RuntimeException(
                    "Database connection failed: " + e.getMessage(),
                    e
            );
        }
    }
}