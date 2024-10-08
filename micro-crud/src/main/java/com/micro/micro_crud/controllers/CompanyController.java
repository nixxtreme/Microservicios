package com.micro.micro_crud.controllers;

import com.micro.micro_crud.entities.Company;
import com.micro.micro_crud.services.CompanyService;
import io.micrometer.core.annotation.Timed;
import io.micrometer.observation.annotation.Observed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping(path = "company")
@Slf4j
@Tag(name = "Companies resource")
public class CompanyController {
    private final CompanyService companyService;

    @Operation(summary = "Obtener compañía por nombre")
    @GetMapping(path = "{name}")
    @Observed(name = "company.name")
    @Timed(value = "company.name")
    public ResponseEntity<Company>get(@PathVariable String name){

        log.info("GET: company{}", name);
        return ResponseEntity.ok(this.companyService.readByName(name));
    }
    @Operation(summary = "Agregar nueva compañía a la BD")
    @PostMapping
    public ResponseEntity<Company> post(@RequestBody Company company){
        log.info("POST: company{}", company.getName());
        return ResponseEntity.created(URI.create(this.companyService.create(company).getName())).build();
    }
    @Operation(summary = "Actualizar nueva compañía a la BD")
    @PutMapping(path = "{name}")
    public ResponseEntity<Company> put(@RequestBody Company company, @PathVariable String name){
        log.info("PUT: company{}", name);
        return ResponseEntity.ok(this.companyService.update(company, name));
    }
    @Operation(summary = "Borrar compañía a la BD")
    @DeleteMapping(path = "{name}")
    public ResponseEntity<?> delete(@PathVariable String name){
        log.info("DELETE: company{}", name);
        this.companyService.delete(name);
        return ResponseEntity.noContent().build();
    }


}
