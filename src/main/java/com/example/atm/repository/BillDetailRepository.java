package com.example.atm.repository;

import com.example.atm.entity.BillDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillDetailRepository extends JpaRepository <BillDetail , Long> {

    BillDetail findByBill(long id);

}
