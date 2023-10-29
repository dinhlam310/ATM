package com.example.atm.service;

import com.example.atm.entity.Bill;
import com.example.atm.entity.BillDetail;
import com.example.atm.repository.BillRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service
public class ExcelService {
    private static Workbook workbook = new XSSFWorkbook();
    private static Sheet sheet = workbook.createSheet("Bill Detail");

    public void createExcel(Bill bill, BillDetail billDetail) {
        try{
            String filePath = "C:\\Users\\Dinh Lam\\JaspersoftWorkspace\\MyReports\\FirstReport.jrxml";

            Map<String , Object> parameters = new HashMap<>();
            parameters.put("studentName","Lam");


//            Date currentDate = new Date(System.currentTimeMillis());
//            Bill bill = new Bill(1L, "Thành công", 18700, "", currentDate);

            List<Bill> list = new ArrayList<>();
            list.add(bill);

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);

            JasperReport report = JasperCompileManager.compileReport(filePath);

            JasperPrint print = JasperFillManager.fillReport(report, parameters, dataSource);

            JasperExportManager.exportReportToPdfFile(print,"C:\\Users\\Dinh Lam\\Desktop\\New Folder\\FirstReport1.pdf" );

            System.out.println("Report Created");
        } catch (Exception e){
            System.out.println("Exception while creating report: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
