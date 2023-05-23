package com.elibrary.group4.service;


import com.elibrary.group4.Specification.BookSpecification;
import com.elibrary.group4.Utils.Constants.IsAvailable;
import com.elibrary.group4.exception.NotFoundException;
import com.elibrary.group4.model.Book;
import com.elibrary.group4.model.Category;
import com.elibrary.group4.model.request.BookRequest;
import com.elibrary.group4.repository.BookRepository;
import com.elibrary.group4.repository.CategoryRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class BookService implements IBookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private IUploadService uploadService;
    @Override
    public Book create(BookRequest bookRequest) throws Exception {
        try {
            String filePath = "";
            Optional<Category> category = categoryRepository.findById(bookRequest.getCategoryId());

            if (category.isEmpty()) {
                throw new NotFoundException("Category is not found");
            }

            if (!bookRequest.getImage().isEmpty()) {
                filePath = uploadService.uploadMaterial(bookRequest.getImage());
            }

            Book book = new Book();
            book.setTitle(bookRequest.getTitle());
            book.setImage(filePath);
            book.setAuthorName(bookRequest.getAuthorName());
            book.setPublisher(bookRequest.getPublisher());
            book.setPublicationYear(bookRequest.getPublicationYear());
            book.setIsAvailable(IsAvailable.AVAILABLE);
            book.setStock(bookRequest.getStock());
            book.setCategory(category.get());
            return bookRepository.save(book);
        } catch (DataIntegrityViolationException e) {
            throw new EntityExistsException();
        }
    }

    @Override
    public Book get(String bookId) throws Exception {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isEmpty()) {
            throw new NotFoundException("Book is not found");
        }
        return book.get();
    }

    @Override
    public void update(BookRequest bookRequest, String bookId) throws Exception {
        try {
            Book existingBook = get(bookId);
            existingBook.setTitle(bookRequest.getTitle());
            existingBook.setAuthorName(bookRequest.getAuthorName());
            existingBook.setPublisher(bookRequest.getPublisher());
            existingBook.setPublicationYear(bookRequest.getPublicationYear());
            existingBook.setStock(bookRequest.getStock());
            if(bookRequest.getStock()>=1){
                existingBook.setIsAvailable(IsAvailable.AVAILABLE);
            }
            else{
                existingBook.setIsAvailable(IsAvailable.NOTAVAILABLE);
            }
            bookRepository.save(existingBook);
        } catch (NotFoundException e) {
            throw new NotFoundException("Update failed because ID is not found!");
        }
    }

    @Override
    public void delete(String bookId) throws Exception {
        try {
            Book existingBook = get(bookId);
            bookRepository.delete(existingBook);
        } catch (NotFoundException e) {
            throw new NotFoundException("Delete failed because ID is not found!");
        }
    }

    @Override
    public List<Book> findByTitleContains(String title) {
        List<Book> books = bookRepository.findByTitleContains(title);
        if (books.isEmpty()) {
            throw new NotFoundException("Book with " + title + " title is not found!");
        }
        return books;
    }

    @Override
    public List<Book> findByAuthorNameContains(String authorName) {
        List<Book> books = bookRepository.findByAuthorNameContains(authorName);
        if (books.isEmpty()) {
            throw new NotFoundException("Book with " + authorName + " author name is not found!");
        }
        return books;
    }

    @Override
    public List<Book> findByPublisherContains(String publisher) {
        List<Book> books = bookRepository.findByPublisherContains(publisher);
        if (books.isEmpty()) {
            throw new NotFoundException("Book with " + publisher + " publisher is not found!");
        }
        return books;
    }

    @Override
    public List<Book> findBookByCategory(String categoryName) {
        List<Book> books = bookRepository.findByCategory(categoryName);
        if (books.isEmpty()) {
            throw new NotFoundException("Book with " + categoryName + " category is not found!");
        }
        return books;
    }

    @Override
    public List<Book> getBy(String keyword, String value) throws Exception {
        return switch (keyword) {
            case "title" -> findByTitleContains(value);
            case "author" -> findByAuthorNameContains(value);
            case "publisher" -> findByPublisherContains(value);
            case "category" -> findBookByCategory(value);
            default -> bookRepository.findAll();
        };
    }

    @Override
    public Page<Book> list(Integer page, Integer size, String direction, String sortBy) throws Exception {
        Sort sort = Sort.by(Sort.Direction.valueOf(direction), sortBy);
        Pageable pageable = PageRequest.of((page -1), size, sort);
        return bookRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Book> listBooksUsingSpecification(Integer page, Integer pageSize, String sortField, String direction, String title, String authorName, String publisher, String category) {


        Sort sort = Sort.by(Sort.Direction.valueOf(direction), sortField);
        Pageable pageable = PageRequest.of((page -1), pageSize, sort);

        Specification<Book> spec = BookSpecification.builder()
                .title(title)
                .authorName(authorName)
                .publisher(publisher)
//                .publicationYear(Integer.parseInt(publicationYear))
                .category(category)
                .build();

        Page<Book> books = bookRepository.findAll(spec, pageable);

        return books;
    }

    private Pageable pageable(int page, int pageSize, String sortField, Sort.Direction sortDirection) {
        return PageRequest.of(page, pageSize);
    }
}
