package com.uptc.repository;

import java.util.Optional;

import com.uptc.models.entities.Employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT s FROM Employee s WHERE s.email = ?1")
    Optional<Employee> findEmployeeByEmail(String email);

}
