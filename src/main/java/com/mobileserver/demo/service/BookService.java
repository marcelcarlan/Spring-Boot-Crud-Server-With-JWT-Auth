package com.mobileserver.demo.service;


import com.mobileserver.demo.domain.Book;
import com.mobileserver.demo.domain.BookCategory;
import com.mobileserver.demo.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @PostConstruct
    private void initDatabase() {
        List<Book> books = new ArrayList<>();
        books.add(new Book("Long Road to Mercy", "David Baldacci", 7.5, BookCategory.Travel));
        books.add(new Book("The Thorn Birds", "Colleen McCullough", 3.5, BookCategory.Fiction));
        books.add(new Book("The Nowhere Man", "Gregg Hurwitz", 5.3, BookCategory.Fiction));
        books.add(new Book("Minimalist Baker's Everyday Cooking", "Dana Shultz", 4.4, BookCategory.Fiction));
        books.add(new Book("Past Tense", "Lee Child", 9.1, BookCategory.Fiction));
        books.add(new Book("Broken Silence", "Natasha Preston", 8.7, BookCategory.Fiction));
        books.add(new Book("Target: Alex Cross", "James Patterson", 6.8, BookCategory.Fiction));
        books.add(new Book("Red Rising", "Pierce Brown", 5.9, BookCategory.Cooking));
        books.add(new Book("Holidays on Ice", "David Sedaris", 9.0, BookCategory.Fiction));
        books.add(new Book("Any Day Now", "Robyn Carr", 7.1, BookCategory.Tech));
        books.add(new Book("The Vendetti Empire", "Sapphire Knight", 2.1, BookCategory.Fiction));
        books.add(new Book("The Blue Bistro", "Elin Hilderbrand", 6.4, BookCategory.Travel));
        books.add(new Book("Killer", "Jonathan Kellerman", 9.2, BookCategory.Fiction));
        bookRepository.saveAll(books);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public Optional<Book> findBook(Long id) {
        return bookRepository.findById(id);
    }

    public boolean deleteBook(Long id) {
        if (findBook(id).isPresent()) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Book> editBook(Book book) {
        return deleteBook(book.getId()) ? Optional.of(saveBook(book)) : Optional.empty();
    }

    public List<Book> addMultipleBooks(List<Book> books) {
        return bookRepository.saveAll(books);
    }
}
