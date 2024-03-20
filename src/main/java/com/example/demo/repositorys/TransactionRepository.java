package com.example.demo.repositorys;

import com.example.demo.TransactionElement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionElement, Long> {
    Optional<TransactionElement> findById(int id);

    Optional<TransactionElement> findByPurposeAndBorrowDate(String purpose, Date borrowDate);

    List<TransactionElement> findAllByPurpose(String purpose);

    //umbauen auf debitorName, Name als hauptschlüssel
    List<TransactionElement> findAllByDebitorId(int debitorId);

    //List<TransactionElement> findAllByTrasactionId(int transactionId);
}
