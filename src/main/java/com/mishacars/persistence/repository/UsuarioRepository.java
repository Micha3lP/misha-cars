package com.mishacars.persistence.repository;

import com.mishacars.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<UserEntity, String> {
    boolean existsByUsername(String username);
}