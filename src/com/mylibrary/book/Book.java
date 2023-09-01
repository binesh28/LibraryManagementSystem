package com.mylibrary.book;

public abstract class Book {
    private final String title;
    private final String author;
    private final String yearOfPublication;
    private final String ISBN;
    private int totalCopies;

    public Book(String title, String author, String yearOfPublication, String ISBN, int totalCopies) {
        this.title = title;
        this.author = author;
        this.yearOfPublication = yearOfPublication;
        this.ISBN = ISBN;
        this.totalCopies = totalCopies;
    }

    public String getTitle() {
        return title;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }

    @Override
    public String toString() {
               return "Book title: " + title  +
                ", Author: " + author +
                ", YearOfPublication: " + yearOfPublication +
                ", ISBN: " + ISBN +
                ", Total copies: " + totalCopies;

    }
}