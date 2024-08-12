package com.ali.employees.controller;

import com.ali.employees.domain.Employee;
import com.ali.employees.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import java.util.List;
import java.util.Optional;

@RestController
public class EmployeeController {
    private final EmployeeService employeeService;
     @Autowired
     public EmployeeController(EmployeeService employeeService) {
         this.employeeService = employeeService;
     }

    @GetMapping(value = "/employee")
    public ResponseEntity<List<Employee>> getEmployees() {
        List<Employee> allEmployees= employeeService.getAllEmployees();
        return new ResponseEntity<>(allEmployees, HttpStatus.OK);
    }

    @GetMapping(value = "/employee/{employeeId}")
    public ResponseEntity<Employee> getEmployee(@PathVariable int employeeId) {
         Optional<Employee> foundEmp = employeeService.getEmployeeById(employeeId);
         if ( foundEmp.isEmpty()){
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
         }
         return new ResponseEntity<>(foundEmp.get(), HttpStatus.OK);
    }


    @PostMapping(value = "/employee/create/{employeeId}")
    public ResponseEntity<Employee> createEmployee(@PathVariable int employeeId, @RequestBody Employee employee) {
         employee.setEmployeeId(employeeId);
         Employee newEmployee = employeeService.createEmployee(employee);
         return new ResponseEntity<>(newEmployee, HttpStatus.CREATED);
    }

    @PutMapping(value = "/employee/update/{employeeId}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable int employeeId, @RequestBody Employee employee) {
         employee.setEmployeeId(employeeId);
         Optional<Employee> foundEmp = employeeService.getEmployeeById(employeeId);
         if ( foundEmp.isEmpty()){
             Employee newEmployee = employeeService.updateEmployee(employee);
             return new ResponseEntity<>(newEmployee, HttpStatus.CREATED);
         }
         Employee updatedEmployee = employeeService.updateEmployee(employee);
         return new ResponseEntity<>(updatedEmployee, HttpStatus.ACCEPTED);
    }


    @DeleteMapping(value = "/employee/delete/{employeeId}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable int employeeId) {
         Optional<Employee> foundEmp = employeeService.getEmployeeById(employeeId);
         if ( foundEmp.isEmpty()){
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
         }
         employeeService.deleteEmployee(employeeId);
         return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
