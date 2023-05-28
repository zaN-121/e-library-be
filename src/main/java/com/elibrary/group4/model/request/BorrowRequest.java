package com.elibrary.group4.model.request;

import com.elibrary.group4.Utils.Constants.BorrowState;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class BorrowRequest {
//    @NotBlank(message = "{invalid.borrow.borrowDate.required}")
//    private LocalDate borrowingDate;
//    @NotBlank(message = "{invalid.borrow.returnDate.required}")
//    private LocalDate returnDate;
//    @NotBlank(message = "{invalid.borrow.borrowState.required}")
//    private BorrowState borrowState;
//    @NotBlank(message = "{invalid.borrow.lateCharge.required}")
//    private Double lateCharge;
    @NotBlank(message = "{invalid.borrow.bookId.required}")
    private String bookId;
}
