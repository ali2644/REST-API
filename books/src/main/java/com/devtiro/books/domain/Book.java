package com.devtiro.books.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {// a class that we are going to use in our service layer
    private String isbn;
    private String author;
    private String title;
}
