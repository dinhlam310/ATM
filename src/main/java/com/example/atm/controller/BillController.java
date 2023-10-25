package com.example.atm.controller;

import com.example.atm.entity.Bill;
import com.example.atm.entity.Money;
import com.example.atm.repository.BillRepository;
import com.example.atm.repository.MoneyRepository;
import com.example.atm.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/api/bill")
public class BillController {
    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillService billService;

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

        Sort sort = Sort.by("dateBill").ascending();
        PageRequest pageRequest = PageRequest.of(page, 5, sort);
        Page<Bill> billPage = billRepository.findAll(pageRequest);

        model.addAttribute("billPage", billPage);

        return "Bill/BillList";
    }

    @GetMapping("/searchBill")
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

//    @PostMapping("/saveBill")
//    public String saveBill(@Valid Bill bill, BindingResult bindingResult ,@RequestParam(defaultValue = "0") int page , Model model) {
//        if (bindingResult.hasErrors()) {
//            billService.createErrorBill(bill.getTotal());
//        }else {
//            billService.createNewBill(bill.getTotal());
//        }
//        return getBills(page, model);
//    }

    @PostMapping("/saveBill")
    public String saveBill(@Valid Bill bill, BindingResult bindingResult, @RequestParam(defaultValue = "0") int page, Model model) {
        if (bindingResult.hasErrors()) {
            try {
                billService.createErrorBill(bill.getTotal());
            } catch (Exception ex) {
                // Xử lý lỗi (nếu cần) hoặc không thực hiện hành động nào
                billService.createErrorBill(bill.getTotal());
            }
        } else {
            billService.createNewBill(bill.getTotal());
        }
        return getBills(page, model);
    }

//    @PostMapping("/saveBill")
//    public String saveBill(@Valid Bill bill, BindingResult bindingResult, @RequestParam(defaultValue = "0") int page, Model model) {
//        try {
//            if (bindingResult.hasErrors()) {
//                throw new ConstraintViolationException((Set<? extends ConstraintViolation<?>>) bindingResult); // Ném ra ConstraintViolationException để bắt lỗi
//            } else {
//                billService.createNewBill(bill.getTotal());
//            }
//        } catch (ConstraintViolationException ex) {
//            billService.createErrorBill(bill.getTotal()); // Gọi hàm xử lý lỗi
//        }
//        return getBills(page, model); // Trả về giao diện danh sách bill (hoặc giao diện khác tùy theo yêu cầu của bạn)
//    }

}
