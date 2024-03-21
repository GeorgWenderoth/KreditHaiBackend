package com.example.demo.repositorys;

import com.example.demo.DebitorElement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DebitorRepository extends JpaRepository<DebitorElement, Long> {

    Optional<DebitorElement> findByItId(int itId);
    Optional<DebitorElement> findByDebitorName(String debitorName);

    Boolean existsByItId(int itId);
    Boolean existsByDebitorName(String debitorName);



}
