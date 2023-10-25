package com.example.atm.controller;

import com.example.atm.entity.Money;
import com.example.atm.repository.MoneyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Controller
@RequestMapping("/api/handle")
public class AtmController {
    @Autowired
    private MoneyRepository moneyRepository;

    @ModelAttribute("MONEY")
    public Money initMoney() {
        return new Money();
    }

    @RequestMapping(value = "moneyList/page", method = RequestMethod.GET)
    public String getMoneys(@RequestParam(defaultValue = "0") int page,Model model) {

        Sort sort = Sort.by("id").ascending();
        PageRequest pageRequest = PageRequest.of(page, 5, sort);
        Page<Money> moneyPage = moneyRepository.findAllMoney(pageRequest);

        model.addAttribute("moneyPage", moneyPage);

        return "Money/moneyList";
    }

    @GetMapping("/updateMoney/{id}")
    public String viewUpdate(@PathVariable("id") Long id,Model  model){
        Money  mn = moneyRepository.findById(id).get();
        if (mn != null){
            model.addAttribute("MONEY",moneyRepository.findById(id));
        }
        return "Money/updateMoney";
    }

    @PostMapping("/saveMoney")
    public String saveMoney(@RequestParam(defaultValue = "0") int page , Model model, Money money) {

        if (moneyRepository.findById(money.getId())  != null){
            //model.addAttribute("isUpdate", "Cập nhật tờ tiền có mã " + money.getId() + " thành công!!");
            moneyRepository.save(money);
        }else{
            return "errorPage";
        }
        return getMoneys(page,model);
    }

}
