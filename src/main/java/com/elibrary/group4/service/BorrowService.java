package com.elibrary.group4.service;

import com.elibrary.group4.exception.NotFoundException;
import com.elibrary.group4.model.Borrow;
import com.elibrary.group4.repository.BorrowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BorrowService implements IService<Borrow> {
    @Autowired
    private BorrowRepository borrowRepository;
    @Override
    public Iterable<Borrow> findAll() {
        return borrowRepository.findAll();
    }

    @Override
    public Borrow add(Borrow params) {
        return borrowRepository.save(params);
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
}
