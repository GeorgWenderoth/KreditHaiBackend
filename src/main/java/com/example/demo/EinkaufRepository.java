package com.example.demo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface EinkaufRepository extends JpaRepository<DebitorElement, Long> { //Long
        Optional<DebitorElement> findByItId(int id); // findByItId, es muss genauso heißen wie der Propertiename, also findByElementID geht nicht, No Property Found for Type
        List<DebitorElement> findAllByStrich(boolean strich);
        void deleteEinkaufElementsByStrichIsTrue();



}
