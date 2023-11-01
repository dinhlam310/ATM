package com.example.atm.repository;

import com.example.atm.entity.SynthesisRecordMoney;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SynthesisRecord extends JpaRepository <SynthesisRecordMoney, Long> {

    @Query("SELECT s FROM SynthesisRecordMoney s ")
    List<SynthesisRecordMoney> findAll();
}
