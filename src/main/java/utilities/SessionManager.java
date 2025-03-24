package utilities;

import objects.User;

public class SessionManager {

    // Store the current logged-in user
    private static User currentUser = null;

    // Set the current logged-in user
    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    // Get the current logged-in user
    public static User getCurrentUser() {
        return currentUser;
    }

    // Check if a user is logged in
    public static boolean isUserLoggedIn() {
        return currentUser != null;
    }

    // Logout the current user
    public static void logoutUser() {
        currentUser = null;
    }
}
