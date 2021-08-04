package com.uptc.api;

import java.util.List;

import com.uptc.models.entities.Employee;
import com.uptc.services.IEmployeeService;
import com.uptc.utils.Url;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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

/***
 * Employee API with cache
 * Example json body:
 * {
        "firstName": "Juanito",
        "lastName": "Caicedo",
        "email": "juan@gov.co",
        "hireDate": "2012-04-23T18:25:43.511Z"
    } 
 */

@RequiredArgsConstructor
@RestController
@RequestMapping(Url.EMPLOYE_RESOURCE)
public class EmployeeResource {

    private final IEmployeeService employeeService;
    
    @PostMapping
    @ResponseStatus(code = CREATED)
    @CachePut(value = "employee-single", key = "#employee.id")
	@CacheEvict(value="employee-list" , key="'getList'")
    public Employee saveEmployee(@RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    @GetMapping
    @ResponseStatus(code = OK)
    @Cacheable(value = "employee-list" , key="'getList'")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("{id}")
    @ResponseStatus(code = OK)
    @Cacheable(value = "employee-single", key = "#employeeId")
    public Employee getEmployeeById(@PathVariable("id") Long employeeId) {
        return employeeService.getEmployeeById(employeeId);
    }

    @PutMapping("{id}")
    @ResponseStatus(code = CREATED)
    @CachePut(value = "employee-single", key = "#employeeId")
	@CacheEvict(value="employee-list", key="'getList'")
    public Employee updateEmployee(@PathVariable("id") Long employeeId,
            @RequestBody Employee employee) {
        return employeeService.updateEmployee(employee, employeeId);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(code = NO_CONTENT)
    @Caching(evict = {
		@CacheEvict(value = "employee-single", key = "#employeeId"),
		@CacheEvict(value="employee-list", key="'getList'")
	})
    public void deleteEmployee(@PathVariable("id") Long employeeId) {
        employeeService.deleteEmployee(employeeId);
    }

}