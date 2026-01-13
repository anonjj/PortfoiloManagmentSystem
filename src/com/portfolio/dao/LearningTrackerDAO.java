package com.portfolio.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for learning tracker operations.
 */
public class LearningTrackerDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/portfolio_db";
    private static final String USER = "root";
    private static final String PASSWORD = "739fhdd7m8";

    public static List<Object[]> getSkillsByUser(int userId) {
        List<Object[]> skills = new ArrayList<>();
        String query = "SELECT skill, proficiency, hours_studied, last_updated FROM learning_tracker WHERE user_id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                skills.add(new Object[] {
                        rs.getString("skill"),
                        rs.getString("proficiency"),
                        rs.getInt("hours_studied"),
                        rs.getTimestamp("last_updated")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return skills;
    }

    public static boolean addSkill(int userId, String skill, String proficiency) {
        String query = "INSERT INTO learning_tracker (user_id, skill, proficiency, hours_studied) VALUES (?, ?, ?, 0)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setString(2, skill);
            stmt.setString(3, proficiency);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateSkill(int userId, String skill, String proficiency, int hoursStudied) {
        String query = "UPDATE learning_tracker SET proficiency = ?, hours_studied = ? WHERE user_id = ? AND skill = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, proficiency);
            stmt.setInt(2, hoursStudied);
            stmt.setInt(3, userId);
            stmt.setString(4, skill);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
