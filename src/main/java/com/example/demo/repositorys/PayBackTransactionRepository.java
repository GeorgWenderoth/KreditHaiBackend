package com.example.demo.repositorys;

import com.example.demo.PayBackTransactionElement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PayBackTransactionRepository extends JpaRepository<PayBackTransactionElement, Long> {
    Optional<PayBackTransactionElement> findById(int Id);

    List<PayBackTransactionElement> findAllByTransactionId(int transactionId);
}
