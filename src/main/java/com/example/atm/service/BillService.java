package com.example.atm.service;

import com.example.atm.entity.Bill;
import com.example.atm.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    public List<Bill> searchDay(int day) {
        return billRepository.findByDay(day);
    }

    public void createNewBill(int amount) {
        Bill bill = new Bill();
        bill.setTotal(amount);
        bill.setStatus("Thành công");
        bill.setDateBill(Date.valueOf(LocalDate.now()));
        billRepository.save(bill);
    }

    public void createErrorBill(int amount) {
        Bill bill = new Bill();
        bill.setTotal(amount);
        bill.setStatus("Thất bại");
        bill.setDateBill(Date.valueOf(LocalDate.now()));
        billRepository.save(bill);
    }


//    public String classifyMoney(int amount){
//
//        int[] denominations = {2000, 500, 200, 100};
//        int[] quantities = {5, 10, 10, 10};
//
//        for (int i = 0; i < denominations.length; i++) {
//            int noteCount = Math.min(amount / denominations[i], quantities[i]);
//            amount -= noteCount * denominations[i];
//            quantities[i] -= noteCount;
//
//            if (noteCount > 0) {
//                System.out.println(noteCount + " note of " + denominations[i]);
//            }
//
//            if (amount == 0) {
//                break;
//            }
//        }
//    }
}
