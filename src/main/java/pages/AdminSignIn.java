package pages;

import db_api.BookDB;
import db_api.UserDB;
import objects.Book;
import objects.User;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import java.util.Scanner;

public class AdminSignIn {
    public static void main() {
        System.out.println("objects.Admin Sign In");
        Scanner scan = new Scanner(System.in);
        signIn(scan);
        int choice;
        do {
            Menu(); // main menu
            while (!scan.hasNextInt()) {
                System.out.println("Please enter a valid option: ");
                scan.nextLine();
            }
            choice = scan.nextInt();
            switch (choice) {
                case 1:
                    viewUserInfo(scan);
                    break;
                case 2:
                    checkoutUserBooks(scan);
                    break;
                case 3:
                    viewBooks();
                    break;
                case 4:
                    searchBooks(scan); // search books method called
                    break;
                case 5:
                    returnUserBooks(scan);
                    break;
                case 6:
                    removeUsers(scan);
                case 7:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Please enter a valid option: ");
            }
        } while (choice != 7); //loops the menu
    }
        public static void signIn (Scanner scan)  // Here the user is able to sign in using their create duser name and password
        {
            System.out.println("-----Admin Sign In-----");

            while (true) {
                System.out.println("Enter Admin Username: ");
                String username = scan.nextLine().trim();
                System.out.println("Enter Password: ");
                String password = scan.nextLine().trim();

                boolean loginSuccess = UserDB.loginAdmin(username, password); // checking if the username and password is saved within the DB
                if (loginSuccess) {
                    break;
                } else {
                    System.out.println("Invalid Username or Password");
                }
            }
        }

    public static void Menu() // menu
    {
        System.out.println("Menu");
        System.out.println("1. View user info");
        System.out.println("2. Checkout books for user");
        System.out.println("3. View books available");
        System.out.println("4. Search books");
        System.out.println("5. Return books for user");
        System.out.println("6. Remove users");
        System.out.println("7. Logout");
        System.out.println("Enter your choice: ");
    }

    public static void viewUserInfo(Scanner scan){
        List<String> usernames = UserDB.getAllUsernames();
        if (usernames.isEmpty()) {
            System.out.println("No users to remove.");
            return;
        }

        System.out.println("----- Registered Users -----");
        for (int i = 0; i < usernames.size(); i++) {
            System.out.println((i + 1) + ". " + usernames.get(i));
        }

        System.out.println("Enter Username to view info: ");
        scan.nextLine();
        String username = scan.nextLine();

        User userToView = UserDB.getUserByUsername(username);

        if(userToView != null) {
            System.out.println("User information: ");
            System.out.println("Username: " + userToView.getUserName());
            System.out.println("User ID: " + userToView.getUserID());

            List<Book> books = userToView.getBooks();
            if(books.isEmpty()) {
                System.out.println("Checked out books: none");
            }else {
                System.out.println("Checked out books: ");
                for (Book book : books) {
                    System.out.println(" - " + book.getTitle());
                }
            }
        }else{
            System.out.println("User not found");
        }
    }


    public static void checkoutUserBooks(Scanner scan) {
        System.out.println("Enter a username to search or press Enter to go back:");
        scan.nextLine();
        String input = scan.nextLine().trim();

        if (input.isEmpty()) {
            return;
        }
        User user = UserDB.getUserByUsername(input);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }
        // Show available books
        List<Book> availableBooks = BookDB.getAllBooks();
        if (availableBooks.isEmpty()) {
            System.out.println("No books available for checkout.");
            return;
        }
        System.out.println("----- Available Books -----");
        for (Book book : availableBooks) {
            System.out.println("ID: " + book.getBookID() + " | Title: " + book.getTitle());
        }
        System.out.println("\nEnter the Book ID to checkout or press Enter to cancel:");
        String idInput = scan.nextLine().trim();
        if (idInput.isEmpty()) {
            return;
        }
        try {
            int bookID = Integer.parseInt(idInput);
            boolean success = UserDB.checkoutBook(user.getUserID(), bookID);

            if (success) {
                System.out.println("Book with ID " + bookID + " checked out for " + user.getUserName());
            } else {
                System.out.println("Failed to checkout book. It might already be checked out.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid Book ID.");
        }
        System.out.println("Press Enter to return to the menu.");
        scan.nextLine();
    }

    public static void viewBooks(){{
        UserSignIn.viewBooks();}
    }

    public static void searchBooks(Scanner scan){
       UserSignIn.searchBooks(scan);
    }


    public static void returnUserBooks(Scanner scan){
        System.out.println("Enter Username to view info: ");

        scan.nextLine();
        String username = scan.nextLine();
        User userToView = UserDB.getUserByUsername(username);

        List<Book> checkedOut = userToView.getBooks();

        if (checkedOut.isEmpty()) {
            System.out.println("Books checked out: none.");
            return;
        }

        System.out.println("Checked out books:");
        for (Book book : checkedOut) {
            System.out.println("----------------");
            System.out.println(book);
        }

        System.out.println("Enter ID of the book to return: ");
        while (!scan.hasNextInt()) {
            System.out.println("Please enter a valid ID: ");
            scan.nextLine();
        }
        int bookID = scan.nextInt();
        // Find the book with the entered ID
        Book bookToReturn = null;
        for (Book book : checkedOut) {
            if (book.getBookID() == bookID) {
                bookToReturn = book;
                break;
            }
        }

        if (bookToReturn == null) {
            System.out.println("Invalid book ID. Book not found in your checked out books.");
            return;
        }

        boolean success = userToView.returnBook(bookToReturn.getTitle());

        if (success) {
            System.out.println("Book returned successfully");
        } else {
            System.out.println("Book could not be returned.");
        }
    }


    public static void removeUsers(Scanner scan) {
        //create list of all users
        List<String> usernames = UserDB.getAllUsernames();
        if (usernames.isEmpty()) {
            System.out.println("No users to remove.");
            return;
        }

        System.out.println("----- Registered Users -----");
        for (int i = 0; i < usernames.size(); i++) {
            System.out.println((i + 1) + ". " + usernames.get(i));
        }

        System.out.println("\nEnter the username to remove or press Enter to go back:");
        scan.nextLine();
        String input = scan.nextLine().trim();

        if (input.isEmpty()) {
            return;
        }

        if (!usernames.contains(input)) {
            System.out.println("User not found.");
            return;
        }

        System.out.println("Are you sure you want to remove user \"" + input + "\"? (yes/no)");
        String confirm = scan.nextLine().trim();

        if (confirm.equalsIgnoreCase("yes")) {
            UserDB.deleteUser(input);
        } else {
            System.out.println("User removal canceled.");
        }

        System.out.println("Press Enter to return to the menu.");
        scan.nextLine();
    }

}
