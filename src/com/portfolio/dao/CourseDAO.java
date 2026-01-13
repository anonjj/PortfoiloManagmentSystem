package com.portfolio.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for course-related database operations.
 */
public class CourseDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/portfolio_db";
    private static final String USER = "root";
    private static final String PASSWORD = "739fhdd7m8";

    public static List<Object[]> getCoursesByCategoryAndLevelAndPrice(String category, String level,
            double minPrice, double maxPrice) {
        List<Object[]> courses = new ArrayList<>();
        String query = "SELECT course_title, price, content_duration, url FROM udemy_courses_cleaned " +
                "WHERE TRIM(LOWER(category)) = ? AND TRIM(LOWER(course_level)) LIKE ? AND price BETWEEN ? AND ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, category.toLowerCase().trim());
            stmt.setString(2, level.equalsIgnoreCase("All Levels") ? "%" : "%" + level.toLowerCase().trim() + "%");
            stmt.setDouble(3, minPrice);
            stmt.setDouble(4, maxPrice);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                courses.add(new Object[] {
                        rs.getString("course_title"),
                        rs.getDouble("price") == 0.0 ? "Free" : "$" + rs.getDouble("price"),
                        rs.getDouble("content_duration") + " hrs",
                        rs.getString("url")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courses;
    }

    public static List<Object[]> getCoursesByCategoryAndLevel(String category, String level) {
        List<Object[]> list = new ArrayList<>();
        String query = "SELECT course_title, price, content_duration, url FROM udemy_courses WHERE TRIM(LOWER(category)) = ?";

        if (!level.equalsIgnoreCase("All Levels")) {
            query += " AND TRIM(LOWER(course_level)) LIKE ?";
        }

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, category.toLowerCase().trim());
            if (!level.equalsIgnoreCase("All Levels")) {
                stmt.setString(2, "%" + level.toLowerCase().trim() + "%");
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Object[] {
                        rs.getString("course_title"),
                        rs.getDouble("price") == 0.0 ? "Free" : "$" + rs.getDouble("price"),
                        rs.getDouble("content_duration") + " hrs",
                        rs.getString("url")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
