package com.micro.micro_crud.services;

import com.micro.micro_crud.entities.Category;
import com.micro.micro_crud.entities.Company;
import com.micro.micro_crud.repositories.CompanyRepository;
import io.micrometer.tracing.Tracer;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final Tracer tracer;

    @Override
    public Company create(Company company) {
        company.getWebsites().forEach(webSite -> {
            if(Objects.isNull(webSite.getCategory())) {
                webSite.setCategory(Category.NONE);
            }
        });
        return this.companyRepository.save(company);
    }

    @Override
    public Company readByName(String name) {
        var span = tracer.nextSpan().name("ReadByName");
        try(Tracer.SpanInScope spanInScope = tracer.withSpan(span)) {
            log.info("Getting company from DB");
        }finally{
            span.end();
        }
        return this.companyRepository.findByName(name).orElseThrow(()-> new NoSuchElementException("Company not found"));
    }

    @Override
    public Company update(Company company, String name) {
        var companyToUpdate = this.companyRepository.findByName(name).orElseThrow(()-> new NoSuchElementException("Company not found"));
        companyToUpdate.setLogo(company.getLogo());
        companyToUpdate.setFoundationDate(company.getFoundationDate());
        companyToUpdate.setFounder(company.getFounder());
        return this.companyRepository.save(companyToUpdate);
    }

    @Override
    public void delete(String name) {
        var companyToDelete = this.companyRepository.findByName(name).orElseThrow(()-> new NoSuchElementException("Company not found"));
        this.companyRepository.delete(companyToDelete);
    }
}
