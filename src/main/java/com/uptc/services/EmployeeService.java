package com.uptc.services;

import java.util.List;
import java.util.Objects;

import com.uptc.exceptions.BadRequestException;
import com.uptc.exceptions.ResourceNotFoundException;
import com.uptc.models.entities.Employee;
import com.uptc.repo.EmployeeRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import static com.uptc.utils.Validations.isEmail;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee employee) {
        if (!existEmail(employee.getEmail()) && isEmail.test(employee.getEmail()))
            return employeeRepository.save(employee);
        throw new BadRequestException(
                String.format("The email %s is not valid or already exists", employee.getEmail()));
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee with Id: "+ id));
    }

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
                && !Objects.equals(employee.getEmail(), existEmployee.getEmail()) && !existEmail(employee.getEmail())
                && isEmail.test(employee.getEmail()))
            existEmployee.setEmail(employee.getEmail());

        employeeRepository.save(existEmployee);
        return existEmployee;
    }

    public void deleteEmployee(Long id) {
        employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee with Id: "+ id));
        employeeRepository.deleteById(id);
    }

    public boolean existEmail(String email) {
        return employeeRepository.findByEmail(email).isPresent();
    }

}