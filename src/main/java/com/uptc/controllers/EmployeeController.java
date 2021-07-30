package com.uptc.controllers;

import java.util.List;

import com.uptc.models.entities.Employee;
import com.uptc.services.EmployeeService;
import com.uptc.utils.Url;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RequiredArgsConstructor
@RestController
@RequestMapping(Url.EMPLOYE_RESOURCE)
public class EmployeeController {

    private final EmployeeService employeeService;
    
    @PostMapping
    @ResponseStatus(code = CREATED)
    public Employee saveEmployee(@RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    @GetMapping
    @ResponseStatus(code = OK)
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("{id}")
    @ResponseStatus(code = OK)
    public Employee getEmployeeById(@PathVariable("id") Long employeeId) {
        return employeeService.getEmployeeById(employeeId);
    }

    @PutMapping("{id}")
    @ResponseStatus(code = CREATED)
    public Employee updateEmployee(@PathVariable("id") Long employeeId,
            @RequestBody Employee employee) {
        return employeeService.updateEmployee(employee, employeeId);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(code = NO_CONTENT)
    public void deleteEmployee(@PathVariable("id") Long employeeId) {
        employeeService.deleteEmployee(employeeId);
    }

}