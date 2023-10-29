package com.example.atm.controller;

import com.example.atm.entity.Bill;
import com.example.atm.entity.BillDetail;
import com.example.atm.repository.BillDetailRepository;
import com.example.atm.repository.BillRepository;
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
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/bill")
public class BillController {
    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillDetailRepository billDetailRepository;

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
        return "Bill/ExportPDF";
    }

}
