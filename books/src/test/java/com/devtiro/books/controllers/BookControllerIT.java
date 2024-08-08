package com.devtiro.books.controllers;

import com.devtiro.books.TestData;
import com.devtiro.books.domain.Book;
import com.devtiro.books.services.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class BookControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookService bookService;

    @Test
    public void testThatBookIsCreated() throws Exception {
        final Book book = TestData.testBook();
        final ObjectMapper objectMapper = new ObjectMapper();
        final String bookJson = objectMapper.writeValueAsString(book);

        mockMvc.perform(MockMvcRequestBuilders.put("/books/"+book.getIsbn())
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(book.getAuthor()));
    }

    @Test
    public void testThatRetrieveBookReturns404WhenBookIsNotFound() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/books/11223"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatRetrieveBookReturns200BookWhenExists() throws Exception {
        final Book book = TestData.testBook();

        bookService.save(book);

        mockMvc.perform(MockMvcRequestBuilders.get("/books/"+book.getIsbn()))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(book.getAuthor()));
        ;
    }

    @Test
    public void testThatFindAllBooksReturns200EmptyListWhenNoBooksExist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/books"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
    }

    @Test
    public void testThatFindAllBooksReturns200BooksWhenBooksExist() throws Exception {

        final Book book = TestData.testBook();

        bookService.save(book);

        mockMvc.perform(MockMvcRequestBuilders.get("/books"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());

    }
    @Test
    public void testThatBookIsUpdatedReturnsHTTP200() throws Exception {
        final Book book = TestData.testBook();
        bookService.save(book);
        book.setAuthor("Ali Mahmoud");
        final ObjectMapper objectMapper = new ObjectMapper();
        final String bookJson = objectMapper.writeValueAsString(book);

        mockMvc.perform(MockMvcRequestBuilders.put("/books/"+book.getIsbn())
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(book.getAuthor()));
    }


    @Test
    public void testThatHttp204IsReturnedWhenBookDoesNotExist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/books/11223"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatHttp204IsReturnedWhenExistingBookIsDeleted() throws Exception {
        final Book book = TestData.testBook();
        bookService.save(book);
        mockMvc.perform(MockMvcRequestBuilders.delete("/books/"+book.getIsbn()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}
