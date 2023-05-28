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
    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "author",nullable = false)
    private String author;
    @Column(name = "thumbnail", nullable = false)
    private String thumbnail;
    @Column(name = "page")
    private Integer page;
    @Column(name = "release_year",nullable = false)
    private String releaseYear;
    @Column(name = "language")
    private String language;
    @Column(name = "is_available",nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private IsAvailable isAvailable;
    @Column(name = "stock",nullable = false)
    private Integer stock;
    @ManyToOne
    private Category category;
}
