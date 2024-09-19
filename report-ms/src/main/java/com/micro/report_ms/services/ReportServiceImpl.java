package com.micro.report_ms.services;

import com.micro.report_ms.Repositories.CompaniesRepository;
import com.micro.report_ms.helpers.ReportHelper;
import com.micro.report_ms.models.Company;
import com.micro.report_ms.models.WebSite;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReportService {

    private final CompaniesRepository companiesRepository;
    private final ReportHelper reportHelper;


    @Override
    public String makeReport(String name) {
        return reportHelper.readTemplate(this.companiesRepository.getByName(name).orElseThrow());
    }

    @Override
    public String saveReport(String report) {
        var format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        var placeholder = this.reportHelper.getPlaceholdersFromTemplate(report);
        var websites = Stream.of(placeholder.get(3))
                .map(website -> WebSite.builder().name(website).build())
                .toList();
        var company = Company.builder()
                .name(placeholder.get(0))
                .foundationDate(LocalDate.parse(placeholder.get(1), format))
                .founder(placeholder.get(2))
                .websites(websites)
                .build();
        this.companiesRepository.postByName(company);
        return "Saved";
    }

    @Override
    public void deleteReport(String name) {
        this.companiesRepository.deteleByName(name);
    }
}
