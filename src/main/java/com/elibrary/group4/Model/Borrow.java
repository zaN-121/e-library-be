package com.elibrary.group4.Model;

import com.elibrary.group4.Utils.Constants.BorrowState;
import jakarta.annotation.Generated;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "tb_borrow")
@Getter @Setter
public class Borrow {
    @Id
    @Generated(value = "UUID")
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "borrow_id")
    private String borrowId;
    @Column(name = "borrowing_date",nullable = false)
    private LocalDate borrowingDate;
    @Column(name = "return_date",nullable = false)
    private LocalDate returnDate;
    @Column(name = "borrow_state", nullable = false)
    private BorrowState borrowState;
    @Column(name = "late_charge",nullable = false)
    private Double lateCharge;
}
