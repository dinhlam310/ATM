package com.example.atm.repository;

import com.example.atm.entity.SynthesisRecordMoney;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SynthesisRecordRepository extends JpaRepository <SynthesisRecordMoney, Long> {

    @Query("SELECT s FROM SynthesisRecordMoney s ")
    SynthesisRecordMoney[] findAllRecord();
}
