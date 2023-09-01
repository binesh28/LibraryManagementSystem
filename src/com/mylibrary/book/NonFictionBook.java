package com.mylibrary.book;

public class NonFictionBook extends Book {

    private final BookType bookType;

    public NonFictionBook(String title, String author, String yearOfPublication, String ISBN, int totalCopies) {
        super(title, author, yearOfPublication, ISBN, totalCopies);
        this.bookType = BookType.NONFICTION;
    }

    @Override
    public String toString() {
        return super.toString() + bookType;
    }
}