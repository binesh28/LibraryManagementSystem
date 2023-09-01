package com.mylibrary.library;

import com.mylibrary.book.Book;
import com.mylibrary.book.BookNotFoundException;
import com.mylibrary.book.FictionBook;
import com.mylibrary.book.NonFictionBook;
import com.mylibrary.user.User;
import com.mylibrary.user.UserNotFoundException;
import com.mylibrary.utils.FileUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.mylibrary.utils.FileUtil.BOOKS_FILE;
import static com.mylibrary.utils.FileUtil.USERS_FILE;


public class Library {
    private final HashMap<String, String> borrowedBooks = new HashMap<>();

    //Add a book to stock
    void addBookToStock(String title, String author, String yearOfPublication, String ISBN, int totalCopies, String bookType) throws IOException {
        Book book;
        switch (bookType.toLowerCase()) {
            case "fiction" -> book = new FictionBook(title, author, yearOfPublication, ISBN, totalCopies);
            case "non-fiction" -> book = new NonFictionBook(title, author, yearOfPublication, ISBN, totalCopies);
            default -> throw new IllegalStateException("Unexpected value: " + bookType.toLowerCase());
        }
        System.out.println("Book successfully added to the inventory.");
        FileUtil.saveRecordToFile(book);
    }

    //Borrow book
    void borrowBook(String userName, String bookTitle) throws IOException {
            Map<String, String> bookMap = searchFileForRecord(bookTitle, "book");
            Map<String, String> userMap = searchFileForRecord(userName, "user");

            if (bookMap.isEmpty()) {
                throw new BookNotFoundException("Book not found in library! " + bookTitle);
            } else if (userMap.isEmpty()) {
                throw new UserNotFoundException("User record not found! " + userName);
            }

            Book bookRecord = (Book) createRecord(bookMap);
            User userRecord = (User) createRecord(userMap);

            if (bookRecord.getTotalCopies() > 0) {
                int totalCopies = bookRecord.getTotalCopies() - 1;
                bookRecord.setTotalCopies(totalCopies);
                System.out.println("Book borrowed -> " + '"' + bookRecord.getTitle() + '"');
                FileUtil.overWriteLineItemInFile(bookRecord);
                borrowedBooks.put(userRecord.getUserName(), bookRecord.getTitle());

            } else {
                System.out.println("There are no copies of this book to borrow!");
            }
    }

    private Object createRecord(Map<String, String> recordMap) {
        if (recordMap.containsValue("Fiction")) {
            return new FictionBook(recordMap.get("Book title").trim(), recordMap.get("Author").trim(), recordMap.get("YearOfPublication").trim(),
                    recordMap.get("ISBN").trim(), Integer.parseInt(recordMap.get("Total copies")));
        } else if (recordMap.containsValue("Non-Fiction")) {
            return new NonFictionBook(recordMap.get("Book title").trim(), recordMap.get("Author").trim(), recordMap.get("YearOfPublication").trim(),
                    recordMap.get("ISBN").trim(), Integer.parseInt(recordMap.get("Total copies")));
        } else {
            return new User(recordMap.get("User ID"), recordMap.get("User Name"));
        }
    }

    private Map<String, String> searchFileForRecord(String searchString, String keyWord) throws IOException {
        final Map<String, String> recordMap = new HashMap<>();
        final Path filePath = keyWord.equals("book") ? Paths.get(BOOKS_FILE) : Paths.get(USERS_FILE);
        List<String> lines = Files.readAllLines(filePath);
        for (String line : lines) {
            String[] lineItem = line.split(",");
            String[] title = lineItem[0].split(":");
            if (title[1].trim().equalsIgnoreCase(searchString.trim())) {
                System.out.println("Success. Found a record in file: " + searchString);
                for (String item : lineItem) {
                    String[] params = item.split(":");
                    recordMap.put(params[0].trim(), params[1].trim());
                }
                break;
            }
        }
        return recordMap;
    }

    //Return book
    void returnBook(String userName, String bookTitle) throws IOException {
        List<String> booksList = borrowedBooks.entrySet().stream()
                .filter(e -> userName.trim().equalsIgnoreCase(e.getKey()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());

        if (booksList.contains(bookTitle.trim())) {
            Map<String, String> bookMap = searchFileForRecord(bookTitle.trim(), "book");
            Book bookRecord = (Book) createRecord(bookMap);
            int totalCopies = bookRecord.getTotalCopies() + 1;
            bookRecord.setTotalCopies(totalCopies); //Increment book count in stock
            System.out.println("Book returned -> " + '"' + bookRecord.getTitle() + '"');
            borrowedBooks.remove(userName.trim(), bookTitle.trim()); //remove user/borrowed book entry
            FileUtil.overWriteLineItemInFile(bookRecord);

        } else {
            System.out.println("User name (or) Borrowed book record not found!");
        }
    }

    //Register a new user
    void addNewUser(String userId, String userName) throws IOException {
        User newUser = new User(userId.trim(), userName.trim());
        System.out.println("User successfully added as a library member.");
        FileUtil.saveRecordToFile(newUser);
    }

    //List all book borrowers
    void listBorrowedBooks() {
        if (borrowedBooks.isEmpty()) {
            System.out.println("Book Borrowers' list is empty!");
        } else {
            borrowedBooks.forEach((key, value) -> System.out.println("User:" + key + "," + " Borrowed book: " + value));
        }
    }
}