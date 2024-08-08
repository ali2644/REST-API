package com.devtiro.books.services;
import com.devtiro.books.domain.Book;

import java.util.List;
import java.util.Optional;


public interface BookService {

    boolean isBookExist(Book book);
    public Book save(Book book);

    public Optional<Book> findById(final String isbn);

    public List<Book> findAll();

    public void deleteById(final String isbn);
}
