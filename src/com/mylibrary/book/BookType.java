package com.mylibrary.book;

enum BookType {
    FICTION("Fiction"),
    NONFICTION("Non-Fiction"),
    ;

    private final String bookType;

    BookType(String bookType) {
        this.bookType = bookType;
    }

    @Override
    public String toString() {
        return ", BookType: " + bookType;
    }
}