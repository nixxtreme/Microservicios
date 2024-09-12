package com.micro.micro_crud.repositories;

import com.micro.micro_crud.entities.WebSite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebSiteRepository extends JpaRepository<WebSite, Long> {

}
