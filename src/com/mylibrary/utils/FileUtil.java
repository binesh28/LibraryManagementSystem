package com.mylibrary.utils;

import com.mylibrary.book.Book;
import com.mylibrary.user.User;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;

public class FileUtil {

    public static final String BOOKS_FILE = ".//resources//books";
    public static final String USERS_FILE = ".//resources/users";

    public static <T> void saveRecordToFile(T obj) throws IOException {
        final Path filePath = (obj instanceof Book) ? Paths.get(BOOKS_FILE) : Paths.get(USERS_FILE);
        if (obj instanceof Book) {
            if (Files.notExists(filePath)) {
                Files.createFile(Path.of(".//resources//books"));
            }
            Files.writeString(filePath, (Book)obj + System.lineSeparator(), StandardOpenOption.APPEND);

        } else if (obj instanceof User) {
            if (Files.notExists(filePath)) {
                Files.createFile(Path.of(".//resources//users"));
            }
            Files.writeString(filePath, (User)obj + System.lineSeparator(), StandardOpenOption.APPEND);
        }
    }

    public static void loadBooksFromFile() throws IOException {
        try {
            Path filePath = Paths.get(BOOKS_FILE);
            List<String> readLines = Files.readAllLines(filePath);
            if (Files.size(filePath) == 0) {
                System.out.println("No books in the library.");
            } else {
                readLines.forEach(System.out::println);
            }
        } catch (NoSuchFileException e) {
            System.out.println("Error: File not found - " + e.getFile());
        }
    }

    public static void overWriteLineItemInFile(Book book) throws IOException {
        Path filePath = Paths.get(BOOKS_FILE);
        List<String> readLines = Files.readAllLines(filePath);
        for (int i = 0; i < readLines.size(); i++) {
            if (readLines.get(i).contains(book.getTitle())) {
                readLines.set(i, book.toString());
                break;
            }
        }
        Files.write(filePath, readLines, StandardCharsets.UTF_8);
    }
}