package com.warehouse;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("adminName");
        String email = request.getParameter("adminEmailId");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");

        String sql = "INSERT INTO admins " +
                     "(admin_name, email, phone, password) " +
                     "VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, phone);
            statement.setString(4, password);

            int result = statement.executeUpdate();

            if (result > 0) {
                response.sendRedirect("login.html");
            } else {
                response.getWriter().println("Admin registration failed.");
            }

        } catch (Exception e) {
            e.printStackTrace();

            response.getWriter().println(
                    "Error while creating admin account: "
                    + e.getMessage()
            );
        }
    }
}