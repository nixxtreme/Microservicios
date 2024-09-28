package com.micro.report_ms.services;

import com.micro.report_ms.Repositories.CompaniesFallbackRepository;
import com.micro.report_ms.Repositories.CompaniesRepository;
import com.micro.report_ms.helpers.ReportHelper;
import com.micro.report_ms.models.Company;
import com.micro.report_ms.models.WebSite;
import com.micro.report_ms.streams.ReportPublisher;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
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
    private final CompaniesFallbackRepository companiesFallbackRepository;
    private final Resilience4JCircuitBreakerFactory circuitBreakerFactory;
    private  final ReportPublisher reportPublisher;

    @Override
    public String makeReport(String name) {
        var circuitBreaker = this.circuitBreakerFactory.create("companies-circuitbreaker");
        return circuitBreaker.run(
                () -> this.makeReportMain(name),
                throwable -> this.makeReportFallout(name, throwable)
        );
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
        this.reportPublisher.publishReport(report);
        this.companiesRepository.postByName(company);
        return "Saved";
    }

    @Override
    public void deleteReport(String name) {
        this.companiesRepository.deteleByName(name);
    }
    private String makeReportMain(String name) {
        return reportHelper.readTemplate(this.companiesRepository.getByName(name).orElseThrow());
    }
    private String makeReportFallout(String name, Throwable error) {
        log.warn(error.getMessage());
        return reportHelper.readTemplate(this.companiesFallbackRepository.getByName(name));
    }
}
