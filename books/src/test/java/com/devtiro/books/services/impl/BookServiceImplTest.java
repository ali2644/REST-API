package com.devtiro.books.services.impl;

import com.devtiro.books.domain.Book;
import com.devtiro.books.domain.BookEntity;
import com.devtiro.books.repositories.Bookrepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.devtiro.books.TestData.testBook;
import static com.devtiro.books.TestData.testBookEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {
    @Mock
    //Diese Annotation erstellt ein Mock-Objekt für Bookrepository. Ein Mock-Objekt ist ein simuliertes Objekt,
    // das Verhalten von echten Objekten nachahmt.
    private Bookrepository bookrepository;
    @InjectMocks
    private BookServiceImpl underTest;

    @Test
    public void testThatBookIsSaved(){
        final Book book = testBook();

        final BookEntity bookEntity = testBookEntity();

        //Mock-Verhalten definieren
        when(bookrepository.save(eq(bookEntity))).thenReturn(bookEntity);

        final Book result = underTest.save(book);

        assertEquals(book, result);
    }

    @Test
    public void testThatFoundByIdReturnsemptyWhenNoBook(){
        final String isbn = "00221122";
        when(bookrepository.findById(eq(isbn))).thenReturn(Optional.empty());
        final Optional<Book> result = underTest.findById(isbn);
        assertEquals(Optional.empty(), result);
    }

    @Test
    public void testThatFindByIdReturnsBookWhenExists(){
        final Book book = testBook();
        final BookEntity bookEntity = testBookEntity();
        when(bookrepository.findById(eq(book.getIsbn()))).thenReturn(Optional.of(bookEntity));
        final Optional<Book> result = underTest.findById(book.getIsbn());
        assertEquals(Optional.of(book), result);
    }

    @Test
    public void testThatFindAllReturnsEmptyListWhenNoBooks(){
        when(bookrepository.findAll()).thenReturn(new ArrayList<BookEntity>());
        final List<Book> result = underTest.findAll();
        assertEquals(0, result.size());
    }

    @Test
    public void testThatFinAllReturnsBooks(){
        final BookEntity bookEntity = testBookEntity();
        when(bookrepository.findAll()).thenReturn(List.of(bookEntity));
        final List<Book> result = underTest.findAll();
        assertEquals(1, result.size());
    }

    @Test
    public void testBookExistsReturnsTrueWhenBookExists(){
        final Book book = testBook();
        final BookEntity bookEntity = testBookEntity();

        when(bookrepository.existsById(eq(book.getIsbn()))).thenReturn(true);
        final boolean result = underTest.isBookExist(book);
        assertEquals(true, result);

    }

    @Test
    public void testBookExistsReturnsFalseWhenBookDoesNotExists(){
        when(bookrepository.existsById(any())).thenReturn(false);
        final boolean result = underTest.isBookExist(testBook());
        assertEquals(false, result);
    }

    @Test
    public void testDeleteBookDeletesBook(){
        final String isbn = "00221122";
        underTest.deleteById(isbn);
        verify(bookrepository,times(1)).deleteById(eq(isbn));
    }

}
