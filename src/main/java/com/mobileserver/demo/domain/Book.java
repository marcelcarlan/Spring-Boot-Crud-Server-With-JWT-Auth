package com.mobileserver.demo.domain;

import javax.persistence.*;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    private String title;

    private String author;

    private Double rating;

    private BookCategory bookCategory;

    public Book(){}
    public Book(String title, String author, Double rating, BookCategory bookCategory){
        this.title=title;
        this.author=author;
        this.rating=rating;
        this.bookCategory=bookCategory;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public BookCategory getBookCategory() {
        return bookCategory;
    }

    public void setBookCategory(BookCategory bookCategory) {
        this.bookCategory = bookCategory;
    }



    public static class Builder{
        private Book book = new Book();
        public Builder withTitle(String title){
            this.book.setTitle(title);
            return this;
        }
        public Builder withAuthor(String author){
            this.book.setAuthor(author);
            return this;
        }
        public Builder withRating(Double rating){
            this.book.setRating(rating);
            return this;
        }
        public Builder withBookCategory(BookCategory bookCategory){
            this.book.bookCategory = bookCategory;
            return this;
        }
        public Book build(){
            return book;
        }
    }
}
