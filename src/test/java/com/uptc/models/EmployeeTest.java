package com.uptc.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Set;

import com.uptc.models.entities.Employee;

import javax.validation.ValidatorFactory;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class EmployeeTest {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    
    @Test
    void yearsOfSeniorityTest(){
        Employee employee = new Employee("pepe", "perez", "lol@gmail.com", LocalDate.of(2003, 01, 01));
        assertEquals(employee.getYearsOfSeniority(), 18);
    }
    
    @Test
    void badHireDateTest(){
        Employee employee = new Employee("pepe", "perez", "lol@gmail.com", LocalDate.of(2030, 01, 01));
        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
        assertEquals(1, violations.size());  // by hiredate
        employee.setEmail("bad"); // +1 email format
        employee.setFirstName(""); // +2 empty and size range
        violations = validator.validate(employee);
        assertEquals(4, violations.size());  // by hiredate
    }

    
}