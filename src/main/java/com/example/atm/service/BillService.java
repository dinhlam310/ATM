package com.example.atm.service;

import com.example.atm.entity.Bill;
import com.example.atm.entity.BillDetail;
import com.example.atm.entity.Money;
import com.example.atm.repository.BillDetailRepository;
import com.example.atm.repository.BillRepository;
import com.example.atm.repository.MoneyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    private final MoneyRepository moneyRepository;

    private final BillDetailRepository billDetailRepository;

    @Autowired
    public BillService(MoneyRepository moneyRepository, BillDetailRepository billDetailRepository) {
        this.moneyRepository = moneyRepository;
        this.billDetailRepository = billDetailRepository;
    }

    public List<Bill> searchDay(int day) {
        return billRepository.findByDay(day);
    }

    public void createNewBill(int amount) {
        Bill bill = new Bill();
        if( checkStorage(amount)){
        bill.setTotal(amount);
        bill.setStatus("Thành công");
        bill.setMessage("Không");
        bill.setDateBill(Date.valueOf(LocalDate.now()));
        billRepository.save(bill);
        classifyMoney(amount , bill.getId());
        }else {
            createErrorBill(0,amount, "Không đủ tiền để rút : ");
        }
    }

    public void createErrorBill(int amount, int total, String message) {
        Bill bill = new Bill();
        bill.setTotal(amount);
        bill.setStatus("Thất bại");
        if (message != null) {
            bill.setMessage(message + total);
        } else {
            bill.setMessage("Số tiền rút quá lẻ : " + total);

        }
        bill.setDateBill(Date.valueOf(LocalDate.now()));
        billRepository.save(bill);
    }

    public boolean checkStorage(int amount) {
        List<Money> moneyList = moneyRepository.findAll();
        int temp = 0;
        for (Money money : moneyList) {
            int denomination = Integer.parseInt(money.getName());
            int quantity = money.getQuantity();
            int totalValue = quantity * denomination;
            temp += totalValue;
        }
        if (temp < amount) {
            return false;
        }
        return true;
    }

    public void classifyMoney(int amount, long id) {
        List<Money> moneyList = moneyRepository.findAll();
        for (Money money : moneyList) {
            int denomination = Integer.parseInt(money.getName());
            int quantity = money.getQuantity();

            int noteCount = Math.min(amount / denomination, quantity);

            amount -= noteCount * denomination;
            quantity -= noteCount;

            if (noteCount > 0) {
                System.out.println(noteCount + " note of " + denomination);
            }
            // noteCount : đếm số lượng tờ
            // denomination : mệnh giá của tiền
            // quantity : số lượng trong kho
            updateMoneyQuantity(String.valueOf(denomination), noteCount , quantity, id);

        }
    }

    public void updateMoneyQuantity(String name, int noteCount, int quantity, long id) {
        Money money = moneyRepository.findByName(name);

        money.setQuantity(quantity);
        moneyRepository.save(money);
        newBillDetail(name, noteCount, id);
    }

    public void newBillDetail(String name, int noteCount, long id){
        Money money = moneyRepository.findByName(name);
        Bill bill = billRepository.findBillById(id);
        BillDetail billDetail = new BillDetail();
        billDetail.setBill(bill);
        billDetail.setMoney(money);
        billDetail.setQuantity(noteCount);
        billDetailRepository.save(billDetail);

    }

    public int TotalQuantityByDateAndMoneyId(Date dateBill , Money moneyId){
        return billDetailRepository.getTotalQuantityByDateAndMoneyId(dateBill,moneyId);
    }
}
