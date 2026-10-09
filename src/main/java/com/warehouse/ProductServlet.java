
package com.warehouse;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/addProduct")
public class ProductServlet extends HttpServlet {

    // =========================
    // ADD PRODUCT
    // =========================

    @Override
    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {

        String productName = request.getParameter("productName");
        String sku = request.getParameter("sku");
        String category = request.getParameter("category");
        String price = request.getParameter("price");
        String quantity = request.getParameter("quantity");

        String sql = "INSERT INTO products " +
                     "(product_name, sku, category, price, quantity) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, productName);
            statement.setString(2, sku);
            statement.setString(3, category);
            statement.setDouble(4, Double.parseDouble(price));
            statement.setInt(5, Integer.parseInt(quantity));

            int result = statement.executeUpdate();

            if (result > 0) {

                response.sendRedirect("products.html");

            } else {

                response.setContentType("text/html");

                response.getWriter().println(
                        "<h2>Product was not added.</h2>"
                );

                response.getWriter().println(
                        "<a href='products.html'>Back to Products</a>"
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.setContentType("text/html");

            response.getWriter().println(
                    "<h2>Product Error</h2>"
            );

            response.getWriter().println(
                    "<p>" + e.getMessage() + "</p>"
            );

            response.getWriter().println(
                    "<a href='products.html'>Back to Products</a>"
            );
        }
    }


    // =========================
    // GET ALL PRODUCTS
    // =========================

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String sql = "SELECT product_id, product_name, sku, " +
                     "category, price, quantity " +
                     "FROM products";

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            StringBuilder json = new StringBuilder();

            json.append("[");

            boolean first = true;

            while (result.next()) {

                if (!first) {
                    json.append(",");
                }

                json.append("{");

                json.append("\"id\":")
                    .append(result.getInt("product_id"))
                    .append(",");

                json.append("\"name\":\"")
                    .append(result.getString("product_name"))
                    .append("\",");

                json.append("\"sku\":\"")
                    .append(result.getString("sku"))
                    .append("\",");

                json.append("\"category\":\"")
                    .append(result.getString("category"))
                    .append("\",");

                json.append("\"price\":")
                    .append(result.getDouble("price"))
                    .append(",");

                json.append("\"quantity\":")
                    .append(result.getInt("quantity"));

                json.append("}");

                first = false;
            }

            json.append("]");

            response.getWriter().print(json.toString());

        } catch (Exception e) {

            e.printStackTrace();

            response.setStatus(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            );

            response.getWriter().print(
                    "{\"error\":\"" + e.getMessage() + "\"}"
            );
        }
    }
}
