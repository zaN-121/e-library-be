package com.elibrary.group4.service;

import com.elibrary.group4.Utils.Constants.BorrowState;
import com.elibrary.group4.Utils.Constants.IsAvailable;
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
import java.util.*;

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
        Iterable<Borrow> borrows = borrowRepository.findAll();
        List<Book>userBooks = new ArrayList<>();
        for (Borrow borrow:borrows) {
            if(borrow.getUser().getUserId().equals(params.getUser().getUserId()) && (borrow.getBorrowState().equals(BorrowState.TAKEN) || borrow.getBorrowState().equals(BorrowState.CANBETAKE))){
                userBooks.add(borrow.getBook());
            } else if (borrow.getUser().equals(params.getUser())&&borrow.getBorrowState().equals(BorrowState.LATE)){
                throw new RuntimeException("Sorry, user has a borrowed overtimed book that hasn't returned yet");
            }
        }
        if(userBooks.size() <= 3){
            if (bookStock>0){
                if(params.getBook().getStock()==1){
                    params.getBook().setIsAvailable(IsAvailable.NOTAVAILABLE);
                }
                params.setBorrowState(BorrowState.CANBETAKE);
                bookRepository.stockDecrease(params.getBook().getBookId());
                return borrowRepository.save(params);
            }
            else{
                throw new RuntimeException("Book with id "+ params.getBook().getBookId()+"run out of stock");
            }

        }
        else{
            throw new RuntimeException("This user has already has three borrowed book with taken status");
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
            bookRepository.updateAvailable(id);
        }
        borrowRepository.updateLateTake();

    }

    @Scheduled(fixedRate = 86400000)
    public void lateReturn(){
        borrowRepository.updateLateReturn();
    }

    public void adminValidateBorrow (String id){
        Optional<Borrow> borrow = borrowRepository.findById(id);
        borrow.ifPresent(value -> value.setBorrowState(BorrowState.TAKEN));
        borrowRepository.save(borrow.get());
    }

    public void adminReturnedBook(String id){
        Optional<Borrow> borrow = borrowRepository.findById(id);
        if(borrow.isPresent()){
            borrow.get().setBorrowState(BorrowState.RETURN);
            Integer bookStock = borrow.get().getBook().getStock();
            borrow.get().getBook().setStock(bookStock+1);
            borrow.get().getBook().setIsAvailable(IsAvailable.AVAILABLE);
            borrowRepository.save(borrow.get());
        }
    }

}
