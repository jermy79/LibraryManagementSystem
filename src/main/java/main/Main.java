package main;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);

        System.out.println("Welcome to the Library Management System" +
                "/n Please Select an Option:" +
                "/n1: User Sign In" +
                "/n2: Admin Sign In" +
                "/n3: Create Account");

        System.out.print("Enter option: ");

        int option = userInput.nextInt();

        switch (option) {}
    }
}
