package com.uptc.models.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.*;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "employees")
@NoArgsConstructor
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 20)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @NotBlank
    @Size(min = 3, max = 20)
    @Column(name = "last_name", length = 50)
    private String lastName;

    @NotBlank
    @Size(max = 50)
    @Email
    @Column(name = "email", unique = true, nullable = false, length = 100)
    private String email;

    @Past
    @Column(name = "hire_date")
    private LocalDate hireDate;

    public Employee(String firstName, String lastName, String email, LocalDate hireDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.hireDate = hireDate;
    }

    public int getYearsOfSeniority(){
        return Period.between(this.getHireDate(), LocalDate.now()).getYears();
    }   
        
}