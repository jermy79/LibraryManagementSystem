package pages;

// Once the user is signed in, the user is able to view their checked out books, checkout books, view books avaliable, search books, and return books

import db_api.UserDB;
import db_api.BookDB;
//import objects.User;
import objects.Book;
//import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// dshsgdnkjbnfkdjan

public class UserSignIn {
    public static void main() {
        System.out.println("objects.User Sign In");
        Scanner scan = new Scanner(System.in);
        signIn(scan); // user must sign in before viewing menu items
        int choice;
        do {
            Menu(); // main menu
            while (!scan.hasNextInt())
            {
                System.out.println("Please enter a valid option: ");
                scan.nextLine();
                Menu();
            }
            choice = scan.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("View Books Checked Out");
                    break;
                case 2:
                    System.out.println("Checkout Books");
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
                    break;
                default:
                    System.out.println("Invalid Choice");
            }
        }while(choice < 1 || choice > 5);
    }

    public static void signIn(Scanner scan)  // Here the user is able to sign in using their create duser name and password
    {
        System.out.println("-----Sign In-----");

        while (true)
        {
            System.out.println("Enter Username: ");
            String username = scan.nextLine().trim();
            System.out.println("Enter Password: ");
            String password = scan.nextLine().trim();

            boolean loginSuccess = UserDB.loginUser(username, password); // checking if the username and password is saved within the DB
            if(loginSuccess)
            {
                break;
            }
            else
            {
                System.out.println("Invalid Username or Password");
            }
        }

    }

        public static void Menu () // menu
        {
            System.out.println("Menu");
            System.out.println("1. View Books Checked Out");
            System.out.println("2. Checkout Books");
            System.out.println("3. View Books Available");
            System.out.println("4. Search Books");
            System.out.println("5. Return Books");
            System.out.println("Enter your choice: ");
        }

        public static void viewChekcedOutBooks ()
        {

        }

        public static void checkoutBooks ()
        {

        }

        public static void viewBooks () // user ids able to view all books avalaible
        {
            List<Book> viewBooks = BookDB.getAllBooks();

            if(viewBooks.isEmpty())
            {
                System.out.println("No Books Found!");
            }
            else
            {
                System.out.println("Here are the Books Available");
                for (Book book : viewBooks)
                {
                    System.out.println(book);
                }
            }

        }

        public static void searchBooks (Scanner scan) // Here the user is able to check if a specfic book is avaliable based on the title
        {
            scan.nextLine();
            System.out.println("Please Enter A Book Title: ");
            String bookTitle = scan.nextLine().trim();

            List<Book> foundBooks = BookDB.searchByTitle(bookTitle); // scanning the list of books in the arrray list

            if (foundBooks.isEmpty())
            {
                System.out.println("Book with Title " + bookTitle + " is not found!");
            }
            else
            {
                System.out.println("Book Found!");
                for(Book book : foundBooks)
                {
                    System.out.println(book);
                }
            }

        }

        public static void returnBooks ()
        {

        }


    }

