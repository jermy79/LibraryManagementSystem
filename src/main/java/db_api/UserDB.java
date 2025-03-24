package db_api;

import objects.Book;
import objects.User;
import utilities.SessionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

public class UserDB {

    // Register a new user
    public static void registerUser(String username, String password) {
        String sql = "INSERT INTO users (username, passwordHash) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);

            stmt.executeUpdate();
            System.out.println("User registered successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete a user
    public static void deleteUser(String username) {
        String sql = "DELETE FROM users WHERE username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            int rowsDeleted = stmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("User deleted successfully.");
            } else {
                System.out.println("User not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean loginUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("passwordHash");
                if (BCrypt.checkpw(password, storedHash)) {
                    // If password matches, create a User object
                    int userID = rs.getInt("userID");
                    String userName = rs.getString("username");
                    String passwordHash = rs.getString("passwordHash");

                    // Retrieve the user's books
                    List<Book> userBooks = getUserBooks(userID);

                    // Create a User object with the retrieved books
                    User loggedInUser = new User(userID, userName, passwordHash, userBooks);

                    // Store the user in the SessionManager
                    SessionManager.setCurrentUser(loggedInUser);
                    System.out.println("User logged in successfully.");
                    return true;
                } else {
                    System.out.println("Invalid password.");
                }
            } else {
                System.out.println("User not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Retrieve the books associated with the user
    private static List<Book> getUserBooks(int userID) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT b.bookID, b.title, b.author, b.publisher, b.isbn, b.checkedOut " + // Ensure `checkedOut` is selected here
                "FROM books b " +
                "JOIN user_books ub ON b.bookID = ub.bookID " +
                "WHERE ub.userID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Ensure `checkedOut` column exists in the result set and is retrieved properly
                books.add(new Book(
                        rs.getInt("bookID"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("publisher"),
                        rs.getString("isbn"),
                        rs.getBoolean("checkedOut") // Make sure to retrieve `checkedOut`
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books; // Will return an empty list if no books are found
    }

    // Logout a user
    public static void logoutUser() {
        SessionManager.logoutUser();
        System.out.println("User logged out successfully.");
    }

    public static User getUserByUsername(String username) {
        String userQuery = "SELECT userID, username, passwordHash FROM users WHERE username = ?";
        String booksQuery = "SELECT b.bookID, b.title, b.author, b.publisher, b.isbn " +
                "FROM books b " +
                "JOIN user_books ub ON b.bookID = ub.bookID " +
                "JOIN users u ON ub.userID = u.userID " +
                "WHERE u.username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement userStmt = conn.prepareStatement(userQuery)) {

            userStmt.setString(1, username);
            ResultSet userRs = userStmt.executeQuery();

            if (userRs.next()) {
                int userID = userRs.getInt("userID");
                String passwordHash = userRs.getString("passwordHash");

                // Fetch books for the user
                List<Book> books = new ArrayList<>();
                try (PreparedStatement booksStmt = conn.prepareStatement(booksQuery)) {
                    booksStmt.setString(1, username);
                    ResultSet booksRs = booksStmt.executeQuery();

                    while (booksRs.next()) {
                        books.add(new Book(
                                booksRs.getInt("bookID"),
                                booksRs.getString("title"),
                                booksRs.getString("author"),
                                booksRs.getString("publisher"),
                                booksRs.getString("isbn"),
                                booksRs.getBoolean("checkedOut")
                        ));
                    }
                }

                return new User(userID, username, passwordHash, books);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if user is not found
    }
}
