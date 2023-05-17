package com.elibrary.group4.repository;

import com.elibrary.group4.model.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BorrowRepository  extends JpaRepository<Borrow, String> {
    public List<Borrow> findBorrowByUserUserId(String id);

    @Query("SELECT book.bookId FROM Borrow e WHERE e.borrowState = com.elibrary.group4.Utils.Constants.BorrowState.CANBETAKE")
    public List<String> findLateTakeId();

    @Modifying
    @Transactional
    @Query("UPDATE Borrow e SET e.borrowState = com.elibrary.group4.Utils.Constants.BorrowState.DECLINE WHERE e.borrowState = com.elibrary.group4.Utils.Constants.BorrowState.CANBETAKE AND e.borrowingDate < DATEADD(HOUR, -24, CURRENT_TIMESTAMP)")
    public void updateLateTake();

    @Modifying
    @Transactional
    @Query("UPDATE Borrow e SET e.lateCharge = e.lateCharge + 1000, e.borrowState = com.elibrary.group4.Utils.Constants.BorrowState.LATE  WHERE e.borrowState = com.elibrary.group4.Utils.Constants.BorrowState.APPROVED OR e.borrowState = com.elibrary.group4.Utils.Constants.BorrowState.LATE AND e.borrowingDate < DATEADD(HOUR, -168, CURRENT_TIMESTAMP)")
    public void updateLateReturn();

}
