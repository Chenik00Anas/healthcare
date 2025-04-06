package com.example.employee_service.repository;

import com.example.employee_service.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);
    
    boolean existsByEmail(String email);
    
    @Query("SELECT e FROM Employee e JOIN e.roles r WHERE r.name = :roleName")
    List<Employee> findByRoleName(String roleName);
    
    @Query("SELECT e FROM Employee e WHERE e.department = :department")
    List<Employee> findByDepartment(String department);
    
    @Query("SELECT DISTINCT e.department FROM Employee e")
    List<String> findAllDepartments();
}