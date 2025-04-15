package db_api;

import objects.Admin;
import objects.Book;
import objects.User;
import utilities.SessionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;

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

    public static boolean registerAdmin(String username, String password, String key) {
        if ("TGJRNqJuwzagKjaWt64pGtKlmlolerji".equals(key)) {
            String sql = "INSERT INTO admin (username, passwordHash) VALUES (?, ?)";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
                stmt.setString(1, username);
                stmt.setString(2, hashedPassword);

                stmt.executeUpdate();
                System.out.println("Admin registered successfully.");
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid key. Please contact an administrator.");
        }
        return false;
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
                    System.out.println("User logged in successfully: ");
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

    public static boolean loginAdmin(String username, String password) {
        String sql = "SELECT * FROM admin WHERE username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("passwordHash");
                if (BCrypt.checkpw(password, storedHash)) {
                    // If password matches, create a User object
                    int userID = rs.getInt("adminID");
                    String userName = rs.getString("username");
                    String passwordHash = rs.getString("passwordHash");

                    // Retrieve the user's books
                    List<Book> userBooks = getUserBooks(userID);

                    // Create a User object with the retrieved books
                    Admin loggedInUser = new Admin(userID, userName, passwordHash, userBooks);

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
    public static List<Book> getUserBooks(int userID) {
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
        String booksQuery = "SELECT b.bookID, b.title, b.author, b.publisher, b.isbn, b.checkedOut " +
                "FROM books b " +
                "JOIN user_books ub ON b.bookID = ub.bookID " +
                "JOIN users u ON ub.userID = u.userID " +
                "WHERE u.username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement userStmt = conn.prepareStatement(userQuery)) {

            // Set the username to search for
            userStmt.setString(1, username);
            ResultSet userRs = userStmt.executeQuery();

            if (userRs.next()) {
                // Get user details from the result set
                int userID = userRs.getInt("userID");
                String passwordHash = userRs.getString("passwordHash");

                // Create list of books for this user
                List<Book> books = new ArrayList<>();
                try (PreparedStatement booksStmt = conn.prepareStatement(booksQuery)) {
                    booksStmt.setString(1, username);  // Set the username to fetch the books
                    ResultSet booksRs = booksStmt.executeQuery();

                    // Fetch books associated with the user
                    while (booksRs.next()) {
                        books.add(new Book(
                                booksRs.getInt("bookID"),
                                booksRs.getString("title"),
                                booksRs.getString("author"),
                                booksRs.getString("publisher"),
                                booksRs.getString("isbn"),
                                booksRs.getBoolean("checkedOut")  // Assume this column exists
                        ));
                    }
                } catch (SQLException e) {
                    System.err.println("Error fetching books for the user: " + e.getMessage());
                    e.printStackTrace();
                }

                // Return the user along with their books
                return new User(userID, username, passwordHash, books);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching user data: " + e.getMessage());
            e.printStackTrace();
        }
        return null; // Return null if user is not found
    }

    public static boolean checkoutBook(int userID, int bookID) {
        // Define the SQL query to check if the user already has the book checked out
        String checkBookQuery = "SELECT * FROM user_books WHERE userID = ? AND bookID = ?";

        // Define the SQL query to update the book's `checkedOut` status
        String updateBookQuery = "UPDATE books SET checkedOut = TRUE WHERE bookID = ?";

        // Define the SQL query to insert the relationship into `user_books`
        String insertUserBookQuery = "INSERT INTO user_books (userID, bookID, checkoutDate, dueDate) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {
            // Check if the user already has the book checked out
            PreparedStatement checkStmt = conn.prepareStatement(checkBookQuery);
            checkStmt.setInt(1, userID);
            checkStmt.setInt(2, bookID);
            var rs = checkStmt.executeQuery();

            if (rs.next()) {
                System.out.println("This book is already checked out by the user.");
                return false;
            }

            // Update the book's `checkedOut` status to TRUE
            PreparedStatement updateStmt = conn.prepareStatement(updateBookQuery);
            updateStmt.setInt(1, bookID);
            updateStmt.executeUpdate();

            // Set the checkout date and due date (14 days from now)
            LocalDate currentDate = LocalDate.now();
            LocalDate dueDate = currentDate.plusDays(14);  // Set the due date to 14 days from now
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDueDate = dueDate.format(formatter);

            // Insert the relationship into the `user_books` table
            PreparedStatement insertStmt = conn.prepareStatement(insertUserBookQuery);
            insertStmt.setInt(1, userID);
            insertStmt.setInt(2, bookID);
            insertStmt.setString(3, currentDate.toString());  // Checkout date
            insertStmt.setString(4, formattedDueDate);       // Due date
            insertStmt.executeUpdate();

            System.out.println("Book checked out successfully. Due date is " + formattedDueDate);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean returnBook(int bookID, int userID) {
        // First, set the book's checkedOut status to false in the books table
        String updateBookSql = "UPDATE books SET checkedOut = false WHERE bookID = ?";
        String deleteUserBookSql = "DELETE FROM user_books WHERE bookID = ? AND userID = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            // Disable the book (mark it as returned)
            try (PreparedStatement updateStmt = conn.prepareStatement(updateBookSql)) {
                updateStmt.setInt(1, bookID);
                int rowsUpdated = updateStmt.executeUpdate();
                if (rowsUpdated == 0) {
                    System.out.println("Failed to mark book as returned (checkedOut = false).");
                    return false;
                }
            }

            // Remove the book from the user's checked out list
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteUserBookSql)) {
                deleteStmt.setInt(1, bookID);
                deleteStmt.setInt(2, userID);

                int rowsAffected = deleteStmt.executeUpdate();
                return rowsAffected > 0; // Return true if the book was removed from user_books table
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static List<String> getAllUsernames() {
        List<String> usernames = new ArrayList<>();
        String query = "SELECT username FROM users";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                usernames.add(rs.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usernames;
    }
}
