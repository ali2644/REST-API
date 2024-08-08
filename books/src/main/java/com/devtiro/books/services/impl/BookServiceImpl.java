package com.devtiro.books.services.impl;

import com.devtiro.books.domain.Book;
import com.devtiro.books.domain.BookEntity;
import com.devtiro.books.repositories.Bookrepository;
import com.devtiro.books.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service//
public class BookServiceImpl implements BookService {

    private final Bookrepository bookrepository;

    @Autowired // was macht Autowired hier
    public BookServiceImpl(final Bookrepository bookrepository) {
        this.bookrepository = bookrepository;
    }

    @Override
    public Book create(final Book book) {
        final BookEntity bookEntity = bookToBookEntity(book);
        final BookEntity savedBookEntity = bookrepository.save(bookEntity);
        return bookEntityToBook(savedBookEntity);
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
