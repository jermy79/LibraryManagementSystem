// Ryan Chaves
import pages.AdminSignIn;
import pages.CreateAccount;
import pages.UserSignIn;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        System.out.println("Welcome to the Library Management System" +
                "\n Please Select an Option:" +
                "\n1: objects.User Sign In" +
                "\n2: objects.Admin Sign In" +
                "\n3: Create Account" +
                "\n4: Exit");

        System.out.print("Enter option: ");

        int option = userInput.nextInt();

        switch (option) {
            case 1:
                UserSignIn.main();
                break;
            case 2:
                AdminSignIn.main();
                break;
            case 3:
                CreateAccount.main();
                break;
            case 4:
                break;
            default:
                System.out.println("error");
        }
    }
}
