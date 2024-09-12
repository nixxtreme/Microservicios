package com.micro.micro_crud.services;

import com.micro.micro_crud.entities.Company;

public interface CompanyService {
    Company create(Company company);
    Company readByName(String name);
    Company update(Company company,String name);
    void delete(String name);
}
