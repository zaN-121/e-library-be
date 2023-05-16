package com.elibrary.group4.model;

import com.elibrary.group4.Utils.Constants.IsAvailable;
import jakarta.annotation.Generated;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

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
    @Column(name = "image", nullable = false)
    private String image;
    @Column(name = "author_name",nullable = false)
    private String authorName;
    @Column(name = "publisher",nullable = false)
    private String publisher;
    @Column(name = "publication_year",nullable = false)
    private LocalDate publicationYear;
    @Column(name = "isAvailable",nullable = false)
    private IsAvailable isAvailable;
    @Column(name = "stock",nullable = false)
    private Integer stock;
    @ManyToOne
    private Category category;
}
