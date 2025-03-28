package pages;
import db_api.UserDB;
import java.util.Scanner;
import static pages.CreateAccount.getValidPassword;
import static pages.CreateAccount.getValidUsername;

public class CreateAdminAccount {

    public static void main(Scanner scan)
    {
        System.out.println("-----Create Admin Account-----");

        //validate Username
        String userName = getValidUsername(scan);

        //validate password
        String password = getValidPassword(scan);

        //validate AdminKey
        String adminKey = getValidAdminkey(scan);

        //sends password, username and adminkey to register in the database
        UserDB.registerAdmin(userName, password, adminKey);

        System.out.println("Admin Account created successfully");
    }

    public static String getValidAdminkey(Scanner scan)
    {
        while(true)
        {
            System.out.println("Enter Admin Key: ");
            String adminKey = scan.nextLine().trim();

            if (adminKey.equals("TGJRNqJuwzagKjaWt64pGtKlmlolerji"))
            {
                return adminKey;
            }
            else {
                System.out.println("Invalid Admin Key");
            }
        }
    }

}
