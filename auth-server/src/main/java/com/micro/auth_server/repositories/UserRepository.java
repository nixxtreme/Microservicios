package com.micro.auth_server.repositories;

import com.micro.auth_server.entitiees.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
