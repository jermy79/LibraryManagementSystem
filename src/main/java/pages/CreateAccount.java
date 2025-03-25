package pages;

import db_api.UserDB;
import objects.User;
import java.util.Scanner;

public class CreateAccount
{
    public static void main()
    {
        Scanner scan = new Scanner(System.in);

        System.out.println("-----Create Account-----");

        //validate Username
        String userName = getValidUsername(scan);

        //validate password
        String password = getValidPassword(scan);

        //sends password and username to register in the database
        UserDB.registerUser(userName, password);

        System.out.println("Account created successfully");

        scan.close();
    }


    public static String getValidUsername(Scanner scan)
    {
        while(true)
        {
            System.out.println("Enter username (4-12 Character): ");
            String userName = scan.nextLine().trim();

            //Check if username is between 4-12 character
            if(userName.length() < 4 || userName.length() > 12)
            {
                System.out.println("Username must be between 4 and 12 characters");
                continue;
            }

            //Checks if username already exist in the DB
            User existingUser = UserDB.getUserByUsername(userName);
            if(existingUser != null)
            {
                System.out.println("Username already exists");
            }
            else{
                System.out.println("Username " + userName + " is valid");
                return userName;
            }

        }
    }

    public static String getValidPassword(Scanner scan)
    {
        while(true)
        {
            //Makes sure user inputs a valid password with number and special character for security
            System.out.println("Enter password (8-16 Character, at least 1 number( 0-9 )\n" +
                    "and 1 special character ( @#$%^&*_+ ): ");
            String password = scan.nextLine().trim();

            if(password.length() < 8 || password.length() > 16)
            {
                System.out.println("Password must be between 8 and 16 characters");
                continue;
            }

            //checks if the password has a number and special char in it
            boolean hasNumber = password.matches(".*\\d+.*");
            boolean hasSpecial = password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*");

            if(!hasNumber || !hasSpecial)
            {
                System.out.println("Password must contain at least 1 number and 1 special character");
                continue;
            }

            //makes user type in password again to make sure
            System.out.println("Confirm Password:");
            String confirmPassword = scan.nextLine().trim();

            //checks if both passwords match
            if(!confirmPassword.equals(password))
            {
                System.out.println("Passwords does not match");
                continue;
            }
            System.out.println("Password is valid");

            return password;
        }
    }
}