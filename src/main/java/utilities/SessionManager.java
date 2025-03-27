package utilities;

import objects.Admin;
import objects.User;

public class SessionManager {

    private static User currentUser = null;

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static boolean isAdminLoggedIn() {
        return currentUser instanceof Admin;
    }

    // Check if a user is logged in
    public static boolean isUserLoggedIn() {
        return currentUser != null && !(currentUser instanceof Admin);
    }
    // Logout the current user
    public static void logoutUser() {
        currentUser = null;
    }

}
