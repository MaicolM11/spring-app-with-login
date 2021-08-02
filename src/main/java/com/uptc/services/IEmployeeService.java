package com.uptc.services;

import java.util.List;

import com.uptc.models.entities.Employee;

public interface IEmployeeService {

    Employee saveEmployee(Employee employee);  
    List<Employee> getAllEmployees();
    Employee getEmployeeById(Long id);
    Employee updateEmployee(Employee employee, Long id);
    void deleteEmployee(Long id);
    boolean existEmail(String email);

}
