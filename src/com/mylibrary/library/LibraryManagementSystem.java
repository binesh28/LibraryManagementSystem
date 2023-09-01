package com.mylibrary.library;

import com.mylibrary.utils.FileUtil;

import java.io.IOException;
import java.util.Scanner;

public class LibraryManagementSystem {
    private static String title;
    private static String author;
    private static String yearOfPublication;
    private static String ISBN;
    private static int totalCopies;
    private static String bookType;
    private static String bookTitle;
    private static String userName;
    private static String userId;

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        Library library = new Library();

        while (true) {
            displayMenu();
            try {
                String userInput = scanner.nextLine();
                if (Integer.parseInt(userInput) == 1) {
                    FileUtil.loadBooksFromFile();
                } else if (Integer.parseInt(userInput) == 2) {
                    getNewBookDetails();
                    library.addBookToStock(title, author, yearOfPublication, ISBN, totalCopies, bookType); //Code looks good.
                } else if (Integer.parseInt(userInput) == 3) {
                    getBookDetails();
                    library.borrowBook(userName, bookTitle);
                } else if (Integer.parseInt(userInput) == 4) {
                    getBookDetails();
                    library.returnBook(userName, bookTitle);
                } else if (Integer.parseInt(userInput) == 5) {
                    getNewUserInfo();
                    library.addNewUser(userId, userName);
                } else if (Integer.parseInt(userInput) == 6) {
                    library.listBorrowedBooks();
                } else if (Integer.parseInt(userInput) == 7) {
                    System.out.println("Exiting!");
                    break;
                } else {
                    System.out.println("Please enter a value between [1-7].");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid option.");
            }
        }
    }

    private static void getNewUserInfo() {
        System.out.print("Enter user name: ");
        userName = scanner.nextLine();
        System.out.print("Enter user ID: ");
        userId = scanner.nextLine();
    }

    private static void getBookDetails() {
        System.out.print("Enter book title: ");
        bookTitle = scanner.nextLine();
        System.out.print("Enter user name: ");
        userName = scanner.nextLine();
    }

    private static void getNewBookDetails() {
        System.out.print("Enter Title: ");
        title = scanner.nextLine();
        System.out.print("Enter Author: ");
        author = scanner.nextLine();
        System.out.print("Enter year of publication: ");
        yearOfPublication = scanner.nextLine();
        System.out.print("Enter ISBN#: ");
        ISBN = scanner.nextLine();
        System.out.print("Enter total copies: ");
        totalCopies = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Book Type(Fiction / Non-Fiction): ");
        bookType = scanner.nextLine();
    }

    private static void displayMenu() {
        System.out.println();
        System.out.println("--------->Library Management System Menu<----------");
        System.out.println("Welcome! Please select one of the options below...");
        System.out.println("1. List available Books (In Stock)");
        System.out.println("2. Add a Book");
        System.out.println("3. Borrow Book");
        System.out.println("4. Return Book");
        System.out.println("5. Register a User");
        System.out.println("6. List of Borrowed Books");
        System.out.println("7. Quit");
        System.out.println("-----------------");
        System.out.print("Enter your choice : ");
    }

}