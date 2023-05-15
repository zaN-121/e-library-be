package com.elibrary.group4.Model;

import com.elibrary.group4.Utils.Constants.IsAvailable;
import jakarta.annotation.Generated;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "tb_book")
@Getter @Setter
public class Book {
    @Id
    @Generated(value = "UUID")
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "book_id")
    private String bookId;
    @Column(name = "title",nullable = false)
    private String title;
    @Column(name = "author_id",nullable = false)
    private String authorId;
    @Column(name = "publisher",nullable = false)
    private String publisher;
    @Column(name = "publication_year",nullable = false)
    private LocalDate publicationYear;
    @Column(name = "isAvailable",nullable = false)
    private IsAvailable isAvailable;
    @Column(name = "stock",nullable = false)
    private Integer stock;
    @OneToMany
    private Category category;
}
