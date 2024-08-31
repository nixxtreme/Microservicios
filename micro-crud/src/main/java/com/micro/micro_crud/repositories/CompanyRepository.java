package com.micro.micro_crud.repositories;

import com.micro.micro_crud.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByName(String name);
}
