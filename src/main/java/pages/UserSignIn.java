package pages;

// Once the user is signed in, the user is able to view their checked out books, checkout books, view books avaliable, search books, and return books

import db_api.UserDB;
//import objects.User;
import java.util.Scanner;

public class UserSignIn {
    public static void main() {
        System.out.println("objects.User Sign In");
        Scanner scan = new Scanner(System.in);

        signIn(scan);
        // create main menu for student log in
        int choice;
        Menu();
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
                break;
            case 4:
                System.out.println("Search Books");
                break;
            case 5:
                System.out.println("Return Books");
                break;
            default:
                System.out.println("Invalid Choice");
        }

    }

    public static void signIn(Scanner scan) {
        System.out.println("-----Sign In-----");

        while (true)
        {
            System.out.println("Enter Username: ");
            String username = scan.nextLine().trim();
            System.out.println("Enter Password: ");
            String password = scan.nextLine().trim();

            boolean loginSuccess = UserDB.loginUser(username, password);
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

        public static void Menu ()
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

        public static void viewBooks ()
        {

        }

        public static void searchBooks ()
        {

        }

        public static void returnBooks ()
        {

        }


    }

