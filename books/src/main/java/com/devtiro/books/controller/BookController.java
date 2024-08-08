package com.devtiro.books.controller;

import com.devtiro.books.domain.Book;
import com.devtiro.books.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    @PutMapping(path = "/books/{isbn}")//{} for declaring a path variable
    public ResponseEntity<Book> createUpdateBook(
            @PathVariable final String isbn,
            @RequestBody final Book book) {

        book.setIsbn(isbn);
        final boolean isBookExists = bookService.isBookExist(book);
        final Book savedBook = bookService.save(book);
        if (isBookExists) {
            return new ResponseEntity<Book>(savedBook, HttpStatus.OK);

        } else {
            final ResponseEntity<Book> response = new ResponseEntity<Book>(savedBook, HttpStatus.CREATED);
            return response;
        }
    }

    @GetMapping(path = "/books/{isbn}")
    public ResponseEntity<Book> retrieveBook(@PathVariable final String isbn) {

        Optional<Book> foundBook = bookService.findById(isbn);

             /*
        if (foundBook.isPresent()) {

            Book book = foundBook.get();
            final ResponseEntity<Book> response = new ResponseEntity<Book>(book, HttpStatus.FOUND);
            return response;
        }
        return ResponseEntity.notFound().build();

              */

        return foundBook.map(book->new ResponseEntity<Book>(book, HttpStatus.FOUND)).orElse(new ResponseEntity<Book>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/books")
    public ResponseEntity<List<Book>> retrieveAllBooks() {
        List<Book> foundBooks= bookService.findAll();
        // we can return an empty List
        return new ResponseEntity<>(foundBooks, HttpStatus.OK);
    }

    @DeleteMapping(path = "/books/{isbn}")
    public ResponseEntity<Book> deleteBook(@PathVariable final String isbn) {
        bookService.deleteById(isbn);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
