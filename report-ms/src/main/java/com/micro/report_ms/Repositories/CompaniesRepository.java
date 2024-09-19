package com.micro.report_ms.Repositories;

import com.micro.report_ms.beans.LoadBalancerConfiguration;
import com.micro.report_ms.models.Company;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@FeignClient(name = "micro-crud")
@LoadBalancerClient(name = "micro-crud", configuration = LoadBalancerConfiguration.class)
public interface CompaniesRepository {

    @GetMapping(path = "/micro-crud/company/{name}")
    Optional<Company> getByName(@PathVariable String name);

    @PostMapping(path = "/micro-crud/company")
    Optional<Company> postByName(@RequestBody Company company);

    @DeleteMapping(path = "/micro-crud/company/{name}")
    void deteleByName(@PathVariable String name);
}
