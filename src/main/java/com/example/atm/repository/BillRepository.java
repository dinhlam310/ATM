package com.example.atm.repository;

import com.example.atm.entity.Bill;
import com.example.atm.entity.Money;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BillRepository extends JpaRepository<Bill,Long> {
    Page<Bill> findAll(Pageable pageable);

    @Query("SELECT s FROM Bill s WHERE DAY(s.dateBill) = :day")
    List<Bill> findByDay(@Param("day") int day);

    Optional<Bill> findById(Long id);

    Bill findBillById(Long id);

    Bill[] findBillByDateBill (Date DateBill);

    Bill save(Bill bill);

    Bill findById(Money id);
}
