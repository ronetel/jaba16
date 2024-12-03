package com.example.pr16;


public class Book {
    private int ID_Book;
    private String book_Name;
    private String book_Author;
    public Book(int ID_Book, String book_Name, String book_Author){
        this.ID_Book=ID_Book;
        this.book_Name=book_Name;
        this.book_Author=book_Author;
    }

    public int getID_Book() {
        return ID_Book;
    }

    public void setID_Book(int ID_Book) {
        this.ID_Book = ID_Book;
    }

    public String getBook_Name() {
        return book_Name;
    }

    public void setBook_Name(String book_Name) {
        this.book_Name = book_Name;
    }

    public String getBook_Author() {
        return book_Author;
    }

    public void setBook_Author(String book_Author) {
        this.book_Author = book_Author;
    }
}