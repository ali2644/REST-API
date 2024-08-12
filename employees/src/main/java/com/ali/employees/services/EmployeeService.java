package com.ali.employees.services;

import com.ali.employees.domain.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    public List<Employee> getAllEmployees();

    public Optional<Employee> getEmployeeById(int id);

    public Employee createEmployee(Employee employee);

    public Employee updateEmployee(Employee employee);

    public void deleteEmployee(int id);
}
