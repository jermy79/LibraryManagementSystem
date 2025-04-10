package pages;

import db_api.UserDB;

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
                    viewUserBooks();
                    break;
                case 4:
                    System.out.println("Search books");
                    searchBooks(scan); // search books method called
                    break;
                case 5:
                    System.out.println("Return books for user");
                    returnUserBooks(scan);
                    break;
                case 6:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Please enter a valid option: ");
            }
        } while (choice != 6); //loops the menu
    }
        public static void signIn (Scanner scan)  // Here the user is able to sign in using their create duser name and password
        {
            System.out.println("-----Sign In-----");

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
        System.out.println("6. Logout");
        System.out.println("Enter your choice: ");
    }
    public static void viewUserInfo(){

    }
    public static void checkoutUserBooks(){

    }
    public static void viewUserBooks(){

    }
    public static void searchBooks(Scanner scan){

    }
    public static void returnUserBooks(Scanner scan){

    }
    }
