package com.uptc.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.uptc.models.entities.Employee;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class EmployeeRepoTest {
    
    @Autowired
    EmployeeRepository repository;

    @Test
    @Order(1)
    @Rollback(value = false)
    void appendEmployeeTest(){
        Employee e = new Employee("Juan", "Caicedo", "Juan@gmail.com", LocalDate.of(2000, 10, 10));
        repository.save(e);
        Optional<Employee> e2 = repository.findByEmail(e.getEmail());
        assertEquals(e2.isPresent(), true);
        assertEquals(e.getEmail(), e2.get().getEmail());
        assertThat(e2.get().getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    void getEmployeeTest(){
        Optional<Employee> employee = repository.findById(1L);
        assertEquals(employee.isPresent(), true);
        assertThat(employee.get().getId()).isEqualTo(1L);
    }

    @Test
    @Order(3)
    public void getListOfEmployeesTest(){
        List<Employee> employees = repository.findAll();
        assertThat(employees.size()).isGreaterThan(0);
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateEmployeeTest(){
        Employee employee = repository.findById(1L).get();
        employee.setEmail("ram@gmail.com");
        Employee employeeUpdated =  repository.save(employee);
        assertThat(employeeUpdated.getEmail()).isEqualTo("ram@gmail.com");
    }

    @Test
    @Order(5)
    public void deleteEmployeeTest(){
        Employee employee = repository.findById(1L).get();
        repository.delete(employee);
        //repository.deleteById(1L);
        Optional<Employee> optionalEmployee = repository.findByEmail("ram@gmail.com");
        assertThat(optionalEmployee.isEmpty()).isTrue();
    }
    
}