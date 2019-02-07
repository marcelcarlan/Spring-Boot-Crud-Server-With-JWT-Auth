package com.mobileserver.demo.web;

import com.mobileserver.demo.domain.Book;
import com.mobileserver.demo.domain.DeleteRequest;
import com.mobileserver.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller("bookController")
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/all")
    public @ResponseBody
    List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @PostMapping("/delete")
    public @ResponseBody
    ResponseEntity<Boolean> deleteBooks(@RequestBody DeleteRequest request) {
        if (request != null && request.getId() != null) {
            if (bookService.deleteBook(request.getId())) {
                return new ResponseEntity<>(Boolean.TRUE, HttpStatus.ACCEPTED);
            }
        }
        return new ResponseEntity<>(Boolean.FALSE, HttpStatus.CONFLICT);
    }

    @PostMapping("/create/multiple")
    public @ResponseBody
    List<Book> saveMultipleBooks(@RequestBody List<Book> books) {
        System.out.println("Add multiple");
        return bookService.addMultipleBooks(books);
    }

    @PostMapping("/create")
    public @ResponseBody
    ResponseEntity<Book> saveBook(@RequestBody Book book) {
        Book savedBook = bookService.saveBook(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Book> updateBook(@RequestBody Book book) {
        Optional<Book> optionalBook = bookService.editBook(book);
        return optionalBook
                .map(book1 -> new ResponseEntity<>(book1, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.CONFLICT));
    }
}
