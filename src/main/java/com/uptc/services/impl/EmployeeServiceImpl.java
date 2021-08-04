package com.uptc.services.impl;

import java.util.List;
import java.util.Objects;

import com.uptc.exceptions.BadRequestException;
import com.uptc.exceptions.ResourceNotFoundException;
import com.uptc.models.entities.Employee;
import com.uptc.repo.EmployeeRepository;
import com.uptc.services.IEmployeeService;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import static com.uptc.utils.Messages.EMAIL_IS_NOT_VALID;
import static com.uptc.utils.Messages.RESOURCE_NOT_FOUND;;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements IEmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public Employee saveEmployee(Employee employee) {
        if (!existEmail(employee.getEmail()))
            return employeeRepository.save(employee);
        throw new BadRequestException(
                String.format(EMAIL_IS_NOT_VALID, employee.getEmail()));
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(RESOURCE_NOT_FOUND, "Employee", "id", id)));
    }

    @Override
    public Employee updateEmployee(Employee employee, Long id) {
        Employee existEmployee = getEmployeeById(id);

        if (employee.getFirstName() != null && employee.getFirstName().length() > 0
                && !Objects.equals(employee.getFirstName(), existEmployee.getFirstName()))
            existEmployee.setFirstName(employee.getFirstName());

        if (employee.getLastName() != null && employee.getLastName().length() > 0
                && !Objects.equals(employee.getLastName(), existEmployee.getLastName()))
            existEmployee.setLastName(employee.getLastName());

        if (employee.getHireDate() != null && !Objects.equals(employee.getHireDate(), existEmployee.getHireDate()))
            existEmployee.setHireDate(employee.getHireDate());

        if (employee.getEmail() != null && employee.getEmail().length() > 0
                && !Objects.equals(employee.getEmail(), existEmployee.getEmail()) && !existEmail(employee.getEmail()))
            existEmployee.setEmail(employee.getEmail());

        employeeRepository.save(existEmployee);
        return existEmployee;
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(RESOURCE_NOT_FOUND, "Employee", "id", id)));
        employeeRepository.deleteById(id);
    }
    
    @Override
    public boolean existEmail(String email) {
        return employeeRepository.findByEmail(email).isPresent();
    }

}