package com.example.employee_service.repository;

import com.example.employee_service.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Optional<Permission> findByName(String name);
    
    boolean existsByName(String name);
    
    @Query("SELECT p FROM Permission p JOIN p.roles r WHERE r.name = :roleName")
    List<Permission> findByRoleName(String roleName);
    
    @Query("SELECT p FROM Permission p WHERE p.name LIKE %:keyword%")
    List<Permission> searchByName(String keyword);
}