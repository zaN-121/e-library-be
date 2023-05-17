package com.elibrary.group4.service;

import com.elibrary.group4.Utils.Constants.BorrowState;
import com.elibrary.group4.exception.NotFoundException;
import com.elibrary.group4.model.Book;
import com.elibrary.group4.model.Borrow;
import com.elibrary.group4.model.User;
import com.elibrary.group4.repository.BookRepository;
import com.elibrary.group4.repository.BorrowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@EnableScheduling
public class BorrowService implements IService<Borrow> {
    @Autowired
    private BorrowRepository borrowRepository;
    @Autowired
    private BookRepository bookRepository;
    @Override
    public Iterable<Borrow> findAll() {
        return borrowRepository.findAll();
    }

    @Override
    public Borrow add(Borrow params) {
        Integer bookStock = params.getBook().getStock();
        if (bookStock>0){
            params.setBorrowState(BorrowState.CANBETAKE);
            bookRepository.stockDecrease(params.getBook().getBookId());
            return borrowRepository.save(params);
        }
        else{
            throw new RuntimeException("Book with id "+ params.getBook().getBookId()+"run out of stock");
        }

    }

    @Override
    public Optional<Borrow> findById(String id) {
        return borrowRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        Optional<Borrow> optionalBorrow;
        try {
            optionalBorrow = borrowRepository.findById(id);
            if (optionalBorrow.isEmpty()) {
                throw new NotFoundException("book borrowing not found");
            }
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
        borrowRepository.delete(optionalBorrow.get());
    }

    @Override
    public Borrow update(String id, Borrow params) {
        Optional<Borrow> optionalBorrow;
        Borrow borrow;
        try {
            optionalBorrow = borrowRepository.findById(id);
            if (optionalBorrow.isEmpty()) {
                throw new NotFoundException("book borrowing not found");
            }

            borrow = optionalBorrow.get();
            borrow.setBorrowingDate(params.getBorrowingDate());
            borrow.setBorrowState(params.getBorrowState());
            borrow.setBook(params.getBook());
            borrow.setUser(params.getUser());
            borrow.setReturnDate(params.getReturnDate());
            borrow.setLateCharge(params.getLateCharge());
        } catch (NotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
        return borrowRepository.save(borrow);
    }

    public List<Borrow> findBorrowByUserId(String id) {
        return borrowRepository.findBorrowByUserUserId(id);
    }

    @Scheduled(fixedRate = 300000)
    public void validateTakeBook(){
        System.out.println(new Date());
        List<String> listLateBookId = borrowRepository.findLateTakeId();
        for (String id: listLateBookId) {
            bookRepository.stockIncrease(id);
        }
        borrowRepository.updateLateTake();

    }

    @Scheduled(fixedRate = 86400000)
    public void lateReturn(){
        borrowRepository.updateLateReturn();
    }

}
