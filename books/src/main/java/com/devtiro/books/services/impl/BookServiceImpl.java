package com.devtiro.books.services.impl;

import com.devtiro.books.domain.Book;
import com.devtiro.books.domain.BookEntity;
import com.devtiro.books.repositories.Bookrepository;
import com.devtiro.books.services.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service//
@Slf4j
public class BookServiceImpl implements BookService {

    private final Bookrepository bookrepository;

    @Autowired // was macht Autowired hier
    public BookServiceImpl(final Bookrepository bookrepository) {
        this.bookrepository = bookrepository;
    }

    @Override
    public boolean isBookExist(Book book) {
        /*
        Optional<BookEntity> foundBook = bookrepository.findById(book.getIsbn());
        if (foundBook.isPresent()) {
            return true;
        }
        return false;

         */
        return bookrepository.existsById(book.getIsbn());
    }

    @Override
    public Book save(final Book book) {
        final BookEntity bookEntity = bookToBookEntity(book);
        final BookEntity savedBookEntity = bookrepository.save(bookEntity);
        return bookEntityToBook(savedBookEntity);
    }
    @Override
    public Optional<Book> findById(final String isbn) {
        // if it does not exist, it will return the optional empty
        final Optional<BookEntity> foundBook=bookrepository.findById(isbn);

        return foundBook.map(bookEntity -> bookEntityToBook(bookEntity));
    }

    @Override
    public List<Book> findAll() {

        List<BookEntity> foundBooksEntities =bookrepository.findAll();
        List<Book> books = foundBooksEntities.stream().map(bookEntity -> bookEntityToBook(bookEntity)).toList();
        return books;
    }

    @Override
    public void deleteById(String isbn) {
        try {
            bookrepository.deleteById(isbn);
        }catch (final EmptyResultDataAccessException e) {
            log.debug("Attempted to delete non-existing book",e);
        }
    }


    private BookEntity bookToBookEntity( Book book) {
       return  BookEntity.builder()
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .author(book.getAuthor())
                .build();
    }

     private Book bookEntityToBook( BookEntity bookEntity) {
       return  Book.builder()
                .isbn(bookEntity.getIsbn())
                .title(bookEntity.getTitle())
                .author(bookEntity.getAuthor())
                .build();
    }
}
