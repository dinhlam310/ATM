package com.example.atm.repository;

import com.example.atm.entity.BillDetail;
import com.example.atm.entity.Money;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;

public interface BillDetailRepository extends JpaRepository <BillDetail , Long> {

    BillDetail findByBill(long id);

    @Query("SELECT SUM(bd.quantity) FROM BillDetail bd INNER JOIN bd.bill b WHERE b.dateBill = :dateBill AND bd.money = :moneyId")
    int getTotalQuantityByDateAndMoneyId(@Param("dateBill") Date dateBill, @Param("moneyId") Money moneyId);
}
