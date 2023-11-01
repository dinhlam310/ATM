package com.example.atm.service;

import com.example.atm.entity.Bill;
import com.example.atm.entity.BillDetail;
import com.example.atm.entity.SynthesisRecordMoney;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExcelService {

    public static String createSynthesisReport(SynthesisRecordMoney[] synthesisRecordMonies, Bill[] bills) {
        try {
            String filePath = "C:\\Users\\Dinh Lam\\JaspersoftWorkspace\\MyReports\\SecondRecord.jrxml";

            Map<String, Object> parameters = new HashMap<>();

            List<Bill> BillList = new ArrayList<>(Arrays.asList(bills));
            List<SynthesisRecordMoney> MoneyList = new ArrayList<>(Arrays.asList(synthesisRecordMonies));

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(BillList);
            JRBeanCollectionDataSource chartDataSource = new JRBeanCollectionDataSource(MoneyList);

            parameters.put("BillList", dataSource);
            parameters.put("MoneyList", chartDataSource);


            JasperReport report = JasperCompileManager.compileReport(filePath);

            JasperPrint print = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());

            JasperExportManager.exportReportToPdfFile(print, "E:\\ATM\\src\\main\\resources\\templates\\JasperReport\\SynthesisReport30.pdf");

            Thread.sleep(2000);

        } catch (Exception e) {
            System.out.println("Exception while creating Synthesis Report: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }


    public String createExcel(Bill bill, BillDetail billDetail) {
        try{
            String filePath = "C:\\Users\\Dinh Lam\\JaspersoftWorkspace\\MyReports\\FirstReport.jrxml";

            Map<String , Object> parameters = new HashMap<>();
            parameters.put("studentName","Lam");

            List<Bill> list = new ArrayList<>();
            list.add(bill);

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);

            JasperReport report = JasperCompileManager.compileReport(filePath);

            JasperPrint print = JasperFillManager.fillReport(report, parameters, dataSource);


            JasperExportManager.exportReportToHtmlFile(print,"E:\\ATM\\src\\main\\resources\\templates\\JasperReport\\htmlExport" + bill.getId() + ".html");

            String filePath1 = "E:\\ATM\\src\\main\\resources\\templates\\JasperReport\\htmlExport" + bill.getId() + ".html";

            Thread.sleep(2000);
            return filePath1;
        } catch (Exception e){
            System.out.println("Exception while creating report: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
