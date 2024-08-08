package com.devtiro.books.repositories;

import com.devtiro.books.domain.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// for all crud-stuff
public interface Bookrepository extends JpaRepository<BookEntity, String> {// String is the type of the Id
}
