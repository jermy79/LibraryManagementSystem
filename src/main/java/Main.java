// Ryan Chaves
//Jeremy Cabrera
//carlos
//Julie Flores
import java.util.Scanner;

import objects.Book;
import pages.AdminSignIn;
import pages.CreateAccount;
import pages.CreateAdminAccount;
import pages.UserSignIn;
import db_api.BookDB;
import utilities.GetBook;

public class Main {
    public static void main(String[] args) {

        Scanner userInput = new Scanner(System.in);
        System.out.println("Welcome to the Library Management System" +
                "\n Please Select an Option:" +
                "\n1: User Sign In" +
                "\n2: Admin Sign In" +
                "\n3: Create Account" +
                "\n4: Create Admin Account" +
                "\n5: Exit");

        System.out.print("Enter option: ");
        String optionInput = userInput.nextLine();
        int option = Integer.parseInt(optionInput);

        switch (option) {
            case 1:
                UserSignIn.main();
                break;
            case 2:
                AdminSignIn.main();
                break;
            case 3:
                CreateAccount.main(userInput);
                pop: UserSignIn.main();
            case 4:
                CreateAdminAccount.main(userInput);
                pop: AdminSignIn.main();
                break;
            case 5:
                break;
            default:
                System.out.println("error");
                break;
        }
        userInput.close();
    }
}
