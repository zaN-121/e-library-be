package com.elibrary.group4.repository;

import com.elibrary.group4.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BookRepository  extends JpaRepository<Book, String> {
    List<Book> findByTitleContains(String title);
    List<Book> findByAuthorNameContains(String authorName);
    List<Book> findByPublisherContains(String publisher);
    @Query("SELECT b from Book b WHERE b.category.name =?1")
    List<Book> findByCategory(String category);

    @Modifying
    @Transactional
    @Query("UPDATE Book e SET e.stock = e.stock+1 WHERE id =?1")
    public void stockIncrease(String id);

    @Modifying
    @Transactional
    @Query("UPDATE Book e SET e.stock = e.stock-1 WHERE id =?1")
    public void stockDecrease(String id);

    @Modifying
    @Transactional
    @Query("UPDATE Book e SET e.isAvailable = com.elibrary.group4.Utils.Constants.IsAvailable.AVAILABLE WHERE id =?1")
    public void updateAvailable(String id);

}
