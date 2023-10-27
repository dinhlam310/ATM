package com.example.atm.service;

import com.example.atm.entity.Bill;
import com.example.atm.entity.Money;
import com.example.atm.repository.BillRepository;
import com.example.atm.repository.MoneyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    private final MoneyRepository moneyRepository;

    @Autowired
    public BillService(MoneyRepository moneyRepository) {
        this.moneyRepository = moneyRepository;
    }

    public List<Bill> searchDay(int day) {
        return billRepository.findByDay(day);
    }

    public void createNewBill(int amount) {
        Bill bill = new Bill();
        bill.setTotal(amount);
        bill.setStatus("Thành công");
        bill.setMessage("");
        bill.setDateBill(Date.valueOf(LocalDate.now()));
        billRepository.save(bill);
        classifyMoney(amount);
    }

    public void createErrorBill(int amount, int total) {
        Bill bill = new Bill();
        bill.setTotal(amount);
        bill.setStatus("Thất bại");
        bill.setMessage("Số tiền khách hàng muốn rút : " + total);
        bill.setDateBill(Date.valueOf(LocalDate.now()));
        billRepository.save(bill);
    }

    public void classifyMoney(int amount) {
        List<Money> moneyList = moneyRepository.findAll();

        for (Money money : moneyList) {
            int denomination = Integer.parseInt(money.getName());
            int quantity = money.getQuantity();

            if (amount == 0) {
                break;
            }

            int noteCount = Math.min(amount / denomination, quantity);
            amount -= noteCount * denomination;
            quantity -= noteCount;

            if (noteCount > 0) {
                System.out.println(noteCount + " note of " + denomination);
            }
            updateMoneyQuantity(String.valueOf(denomination), quantity);
        }
    }

    public void updateMoneyQuantity(String name, int newQuantity) {
        Money money = moneyRepository.findByName(name);

        money.setQuantity(newQuantity);
        moneyRepository.save(money);
    }

}
