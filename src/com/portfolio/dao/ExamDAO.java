package com.portfolio.dao;

import com.portfolio.model.MockQuestion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for exam-related database operations.
 */
public class ExamDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/portfolio_db";
    private static final String USER = "root";
    private static final String PASSWORD = "739fhdd7m8";

    public static List<MockQuestion> getMockQuestions(String examName) {
        List<MockQuestion> questions = new ArrayList<>();
        String query = "SELECT question, option_a, option_b, option_c, option_d, correct_option FROM mock_questions WHERE LOWER(exam_name) = ?";
        System.out.println("Fetching mock questions for: " + examName);

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, examName.trim().toLowerCase());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                questions.add(new MockQuestion(
                        rs.getString("question"),
                        rs.getString("option_a"),
                        rs.getString("option_b"),
                        rs.getString("option_c"),
                        rs.getString("option_d"),
                        rs.getString("correct_option").charAt(0)));
                System.out.println("Fetched MCQ: " + rs.getString("question"));
            }
            System.out.println("Questions found: " + questions.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

    public static String getExamDates(String examName) {
        String query = "SELECT application_date, exam_date, result_date FROM exam_dates WHERE exam_name = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, examName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return "Application: " + rs.getString("application_date") +
                        "\nExam Date: " + rs.getString("exam_date") +
                        "\nResult: " + rs.getString("result_date");
            } else {
                return "Dates not available for " + examName;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "⚠️ Error fetching dates.";
        }
    }

    public static void saveScore(String examName, String email, int score) {
        String query = "INSERT INTO exam_scores (exam_name, user_email, score, taken_at) VALUES (?, ?, ?, NOW())";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, examName);
            stmt.setString(2, email);
            stmt.setInt(3, score);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<String[]> getLeaderboard(String examName) {
        List<String[]> leaderboard = new ArrayList<>();
        String query = """
                    SELECT user_email, MAX(score) as top_score
                    FROM exam_scores
                    WHERE exam_name = ?
                    GROUP BY user_email
                    ORDER BY top_score DESC
                    LIMIT 5
                """;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, examName);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    leaderboard.add(new String[] {
                            rs.getString("user_email"),
                            String.valueOf(rs.getInt("top_score"))
                    });
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return leaderboard;
    }

    public static boolean updateUser(int userId, String newName, String newEmail, String newPassword) {
        String query = "UPDATE users SET name = ?, email = ?, password = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newName);
            stmt.setString(2, newEmail);
            stmt.setString(3, newPassword);
            stmt.setInt(4, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
