package pages;

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
                    System.out.println("View user info");
                    viewUserInfo();
                    break;
                case 2:
                    System.out.println("Checkout books for user");
                    checkoutUserBooks();
                    break;
                case 3:
                    System.out.println("View books available");
                    viewBooks();
                    break;
                case 4:
                    System.out.println("Search books");
                    searchBooks(scan); // search books method called
                    break;
                case 5:
                    System.out.println("Return books for user");
                    returnUserBooks();
                    break;
                case 6:
                    System.out.println("Remove users");
                    removeUsers();
                case 7:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Please enter a valid option: ");
            }
        } while (choice != 6); //loops the menu
    }
        public static void signIn (Scanner scan)  // Here the user is able to sign in using their create duser name and password
        {
            System.out.println("-----Admin Sign In-----");

            while (true) {
                System.out.println("Enter Username: ");
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
    public static void viewUserInfo(){

        //create list of users
        List<String> usernames = UserDB.getAllUsernames();

        //print all users
        if (usernames.isEmpty()) {
            System.out.println("No users found in the system.");
        } else {
            System.out.println("----- Registered Users -----");
            for (int i = 0; i < usernames.size(); i++) {
                System.out.println((i + 1) + ". " + usernames.get(i));
            }

            System.out.println("\nEnter a username to search or press Enter to go back:");
            Scanner scan = new Scanner(System.in);
            String input = scan.nextLine().trim();

            //still need to create code to show userInfo






        }
    }
    public static void checkoutUserBooks(){

    }
    public static void viewBooks(){
        {
            List<Book> viewBooks = BookDB.getAllBooks();

            if (viewBooks.isEmpty()) {
                System.out.println("No Books Found!");
            } else {
                System.out.println("Here are the Books Available");
                for (Book book : viewBooks) {
                    System.out.println("-------------");
                    System.out.println(book);
                }
            }
        }
    }
    public static void searchBooks(Scanner scan){
        scan.nextLine();
        System.out.println("Please Enter A Book Title: ");
        String bookTitle = scan.nextLine().trim();

        List<Book> foundBooks = BookDB.searchByTitle(bookTitle); // scanning the list of books in the arrray list

        if (foundBooks.isEmpty()) {
            System.out.println("Book with Title " + bookTitle + " is not found!");
        } else {
            System.out.println("Book Found!");
            for (Book book : foundBooks) {
                System.out.println(book);
            }
        }
    }
    //fix this error
    public static void returnUserBooks(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter Username to view info: ");

        String username = scan.nextLine();
        User userToView = UserDB.getUserByUsername(username);

        List<Book> checkedOut = userToView.getBooks();

        if (checkedOut.isEmpty()) {
            System.out.println("You have no books checked out.");
            return;
        }

        System.out.println("Your checked out books are:");
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
    public static void removeUsers(){





    }

}
