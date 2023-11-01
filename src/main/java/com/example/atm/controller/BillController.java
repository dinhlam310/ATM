package com.example.atm.controller;

import com.example.atm.entity.Bill;
import com.example.atm.entity.BillDetail;
import com.example.atm.entity.Money;
import com.example.atm.entity.SynthesisRecordMoney;
import com.example.atm.repository.BillDetailRepository;
import com.example.atm.repository.BillRepository;
import com.example.atm.repository.MoneyRepository;
import com.example.atm.repository.SynthesisRecordRepository;
import com.example.atm.service.BillService;
import com.example.atm.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.sql.Date;
import java.util.List;


@Controller
@RequestMapping("/api/bill")
public class BillController {
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private MoneyRepository moneyRepository;
    @Autowired
    private SynthesisRecordRepository synthesisRecordRepository;
    @Autowired
    private BillService billService;
    @Autowired
    private ExcelService excelService;

    @ModelAttribute("BILL")
    public Bill initBill() {
        return new Bill();
    }

    @RequestMapping(value = "/newBill", method = RequestMethod.GET)
    public String newBill(Model model) {
        return "Bill/newBill";
    }

    @RequestMapping(value = "billList/page", method = RequestMethod.GET)
    public String getBills(@RequestParam(defaultValue = "0") int page, Model model) {

        Sort sort = Sort.by("dateBill").descending();
        PageRequest pageRequest = PageRequest.of(page, 5, sort);
        Page<Bill> billPage = billRepository.findAll(pageRequest);

        model.addAttribute("billPage", billPage);

        return "Bill/BillList";
    }

    @RequestMapping(value = "/searchBill", method = RequestMethod.GET)
    public String findBill(UriComponentsBuilder uriBuilder,
                               @RequestParam("Day") int Day, @RequestParam(defaultValue = "0") int page,
                               Model model, HttpServletRequest request) {

        Day = Integer.parseInt(request.getParameter("Day"));
        Pageable pageable = PageRequest.of(0, 5);

        List<Bill> listBill = billService.searchDay(Day);
        Page<Bill> pageBill = new PageImpl<>(listBill, pageable, listBill.size());
        model.addAttribute("billPage", pageBill);

        return "Bill/BillList";
    }

    @PostMapping("/saveBill")
    public String saveBill(@Valid Bill bill, BindingResult bindingResult, @RequestParam(defaultValue = "0") int page, Model model) {
        Bill tempbill = bill;
        Integer total = tempbill.getTotal();
        if (bindingResult.hasErrors()) {
            try {
                billService.createErrorBill(0, total , tempbill.getMessage());
            } catch (Exception ex) {
                billService.createErrorBill(0, total , tempbill.getMessage());
            }
        } else {
            billService.createNewBill(bill.getTotal());
        }
        return getBills(page, model);
    }

    @GetMapping("/generate/{id}")
    public String exportExcel(@PathVariable("id") long id){
        Bill bill =  billRepository.findBillById(id);
//        BillDetail billDetail = billDetailRepository.findByBill(id);
        BillDetail billDetail = null;
        excelService.createExcel(bill,billDetail);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "JasperReport/htmlExport"+bill.getId();
    }

    @RequestMapping(value = "/SynthesisReport", method = RequestMethod.GET)
    public String ViewSynthesisReport() {
        return "Bill/SynthesisReport";
    }

    @GetMapping("/synthesis")
    public String SynthesisReport( Date dateBill) {
        Bill[] bills = billRepository.findBillByDateBill(dateBill);
        SynthesisRecordMoney[] synthesisRecordMonies = synthesisRecordRepository.findAllRecord(); // contain 2 attribute : type of money , and quantity each type of money

        for (int moneyType = 1; moneyType <= 4; moneyType++) {//moneyType = 1 because id of Money table start from 1 to 4
            Money moneyId = moneyRepository.findMoneyById((long) moneyType);// get 4 id for 4 type of money ( 2000,500,200,100)

            //set quantity each money by value of ( date of all bill that we choose on display , and Id of money ) , with 1 dateBill <=> 4 id of money
            synthesisRecordMonies[moneyType-1].setValue(billService.TotalQuantityByDateAndMoneyId(dateBill, moneyId));
        }
        ExcelService.createSynthesisReport(synthesisRecordMonies, bills); // create Jasper Report
        return "Bill/ExportPDF";
    }

}