package com.mylibrary.book;

public class FictionBook extends Book {

    private final BookType bookType;

    public FictionBook(String title, String author, String yearOfPublication, String ISBN, int totalCopies) {
        super(title, author, yearOfPublication, ISBN, totalCopies);
        this.bookType = BookType.FICTION;
    }

    @Override
    public String toString() {
        return super.toString() + bookType;
    }
}