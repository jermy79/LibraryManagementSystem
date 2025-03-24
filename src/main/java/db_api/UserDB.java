//package db_api;
//
//
//import objects.User;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
//public class UserDB {
//
//
//
//
//    public static void registerUser(String username , String password) {
//
//        String sql = "INSERT INTO books (title, author, publisher, isbn) VALUES (?, ?, ?, ?)";
//
//        try (Connection conn = DatabaseConnection.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setString(1, title);
//            stmt.setString(2, author);
//            stmt.setString(3, publisher);
//            stmt.setString(4, isbn);
//
//            stmt.executeUpdate();
//            System.out.println("User added registered.");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void delete(String username , String password) {
//
//        String sql = "INSERT INTO books (title, author, publisher, isbn) VALUES (?, ?, ?, ?)";
//
//        try (Connection conn = DatabaseConnection.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setString(1, title);
//            stmt.setString(2, author);
//            stmt.setString(3, publisher);
//            stmt.setString(4, isbn);
//
//            stmt.executeUpdate();
//            System.out.println("User added registered.");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void loginUser(String username , String password) {
//
//        String sql = "INSERT INTO books (title, author, publisher, isbn) VALUES (?, ?, ?, ?)";
//
//        try (Connection conn = DatabaseConnection.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setString(1, title);
//            stmt.setString(2, author);
//            stmt.setString(3, publisher);
//            stmt.setString(4, isbn);
//
//            stmt.executeUpdate();
//            System.out.println("User added registered.");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void logoutUser(String username , String password) {
//
//        String sql = "INSERT INTO books (title, author, publisher, isbn) VALUES (?, ?, ?, ?)";
//
//        try (Connection conn = DatabaseConnection.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setString(1, title);
//            stmt.setString(2, author);
//            stmt.setString(3, publisher);
//            stmt.setString(4, isbn);
//
//            stmt.executeUpdate();
//            System.out.println("User added registered.");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//}
