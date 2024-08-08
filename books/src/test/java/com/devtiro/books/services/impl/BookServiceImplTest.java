package com.devtiro.books.services.impl;

import com.devtiro.books.domain.Book;
import com.devtiro.books.domain.BookEntity;
import com.devtiro.books.repositories.Bookrepository;
import com.devtiro.books.services.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {
    @Mock
    private Bookrepository bookrepository;
    @InjectMocks
    private BookServiceImpl underTest;

    @Test
    public void testThatBookIsSaved(){
        final Book book = Book.builder()
                .isbn( "02345678")
                .author("Virginia Woolf")
                .title("The Waves")
                .build();

        final BookEntity bookEntity = BookEntity.builder()
                .isbn( "02345678")
                .author("Virginia Woolf")
                .title("The Waves")
                .build();
        when(bookrepository.save(eq(bookEntity))).thenReturn(bookEntity);

        final Book result = underTest.create(book);

        assertEquals(book, result);
    }
}
