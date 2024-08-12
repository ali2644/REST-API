package com.ali.employees.services.impl;

import com.ali.employees.domain.Employee;
import com.ali.employees.domain.EmployeeEntity;
import com.ali.employees.repositories.EmployeeRepository;
import com.ali.employees.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(final EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> Allemployees = employeeRepository.findAll().stream().map(employeeEntity -> EmployeeEntityToEmployee(employeeEntity)).toList();


        return Allemployees;
    }

    @Override
    public Optional<Employee> getEmployeeById(int id) {
        Optional<EmployeeEntity> foundEmployeeEntity =employeeRepository.findById(id);
        return foundEmployeeEntity.map(employeeEntity -> EmployeeEntityToEmployee(employeeEntity));
    }

    @Override
    public Employee createEmployee(Employee employee) {
        EmployeeEntity employeeEntity = EmployeeToEmployeeEntity(employee);

        EmployeeEntity savedEmployeeEntity =employeeRepository.save(employeeEntity);

        return EmployeeEntityToEmployee(savedEmployeeEntity);
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        EmployeeEntity employeeEntity = EmployeeToEmployeeEntity(employee);
        Optional<EmployeeEntity> foundEmployeeEntity = employeeRepository.findById(employeeEntity.getEmployeeId());
        if (foundEmployeeEntity.isPresent()) {
            EmployeeEntity updatedEmployeeEntity = foundEmployeeEntity.get();
            updatedEmployeeEntity.setName(employeeEntity.getName());
            updatedEmployeeEntity.setSalary(employeeEntity.getSalary());
            updatedEmployeeEntity.setAge(employeeEntity.getAge());
            employeeRepository.save(updatedEmployeeEntity);
            return EmployeeEntityToEmployee(updatedEmployeeEntity);
        }
        employeeRepository.save(employeeEntity);
        return EmployeeEntityToEmployee(employeeEntity);

    }

    @Override
    public void deleteEmployee(int id) {
        Optional<EmployeeEntity> foundEmployeeEntity = employeeRepository.findById(id);
        if (foundEmployeeEntity.isPresent()) {
            employeeRepository.deleteById(id);
        }
    }


    private Employee EmployeeEntityToEmployee(EmployeeEntity employeeEntity) {
        return Employee.builder().employeeId(employeeEntity.getEmployeeId())
                .Name(employeeEntity.getName())
                .age(employeeEntity.getAge())
                .salary(employeeEntity.getSalary())
                .build();
    }

    private EmployeeEntity EmployeeToEmployeeEntity(Employee employee) {
        return EmployeeEntity.builder().employeeId(employee.getEmployeeId())
                .Name(employee.getName())
                .age(employee.getAge())
                .salary(employee.getSalary())
                .build();
    }
}
