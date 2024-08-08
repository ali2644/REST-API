package com.devtiro.books.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data//Getters and Setters , Equals and hashcode
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity // to put the book into a table and retrieve it
@Table(name = "books")// it is a table with the name books
public class BookEntity {
    @Id
    private String isbn; // is the primary key
    private String author;
    private String title;

}
