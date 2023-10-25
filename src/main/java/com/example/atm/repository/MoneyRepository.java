package com.example.atm.repository;

import com.example.atm.entity.Money;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

@Repository
public interface MoneyRepository extends JpaRepository<Money, Long> {
        @Query("SELECT m FROM Money m")
        Page<Money> findAllMoney(Pageable pageable);

    Optional<Money> findById(Long id);

    Money save(Money money);

}
