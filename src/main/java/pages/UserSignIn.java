package pages;

// Once the user is signed in, the user is able to view their checked out books, checkout books, view books avaliable, search books, and return books

import db_api.UserDB;
import db_api.BookDB;
//import objects.User;
import objects.Book;
//import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import objects.User;

public class UserSignIn {
    private static User currentUser = null;

    public static void main() {
        System.out.println("objects.User Sign In");
        Scanner scan = new Scanner(System.in);
        signIn(scan); // user must sign in before viewing menu items
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
                    System.out.println("View Books Checked Out");
                    viewCheckedOutBooks();
                    break;
                case 2:
                    System.out.println("Checkout Books");
                    checkoutBooks();
                    break;
                case 3:
                    System.out.println("View Books Available");
                    viewBooks(); // view books method called
                    break;
                case 4:
                    System.out.println("Search Books");
                    searchBooks(scan); // search books method called
                    break;
                case 5:
                    System.out.println("Return Books");
                    returnBooks(scan);
                    break;
                case 6:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Please enter a valid option: ");
            }
        } while (choice != 6); //loops the menu
    }

    public static void signIn(Scanner scan)  // Here the user is able to sign in using their create duser name and password
    {
        System.out.println("-----Sign In-----");

        while (true) {
            System.out.println("Enter Username: ");
            String username = scan.nextLine().trim();
            System.out.println("Enter Password: ");
            String password = scan.nextLine().trim();

            boolean loginSuccess = UserDB.loginUser(username, password); // checking if the username and password is saved within the DB
            if (loginSuccess) {
                currentUser = UserDB.getUserByUsername(username);
                break;
            } else {
                System.out.println("Invalid Username or Password");
            }
        }

    }

    public static void Menu() // menu
    {
        System.out.println("Menu");
        System.out.println("1. View Books Checked Out");
        System.out.println("2. Checkout Books");
        System.out.println("3. View Books Available");
        System.out.println("4. Search Books");
        System.out.println("5. Return Books");
        System.out.println("6. Logout");
        System.out.println("Enter your choice: ");
    }

    public static void viewCheckedOutBooks() {
        List<Book> checkedOut = currentUser.getBooks();
        if (checkedOut==null || checkedOut.isEmpty()) {
            System.out.println("You have no books checked out.");
        } else {
            System.out.println("Your checked out books are:");
            for (Book book : checkedOut) {
                System.out.println(book);
            }
        }
    }

    public static void checkoutBooks() {
        Scanner scan = new Scanner(System.in);
        System.out.println("-----Checkout Books-----");
        System.out.println("Enter book ID to checkout: ");

        while(!scan.hasNextInt()) {
            System.out.println("Please enter a valid option: ");
            scan.nextLine();
        }
        int bookID = scan.nextInt();
        Book book = BookDB.getBookByID(bookID);

        if(book == null){
            System.out.println("Book not found");
            return;
        }
        if(book.isCheckedOut()){
            System.out.println("Book is currently checked out");
            return;
        }
        boolean success = UserDB.checkoutBook(currentUser.getUserID(), bookID);

        if (!success) {
            System.out.println("Book not available or checkout failed.");
        }
        System.out.println(book);
    }

    public static void viewBooks() // user ids able to view all books avalaible
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

    public static void searchBooks(Scanner scan) // Here the user is able to check if a specfic book is avaliable based on the title
    {
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

    public static void returnBooks(Scanner scan) {
        List<Book> checkedOut = currentUser.getBooks();

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

        boolean success = currentUser.returnBook(bookToReturn.getTitle());

        if (success) {
            System.out.println("Book returned successfully");
        } else {
            System.out.println("Book could not be returned.");
        }
    }
}