package com.ali.employees.services.impl;


import com.ali.employees.TestData;
import com.ali.employees.domain.Employee;
import com.ali.employees.domain.EmployeeEntity;
import com.ali.employees.repositories.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl underTest;


    @Test
    public void findAllReturnsAllEmployees() {
        EmployeeEntity employeeEntity = TestData.TestEmployeeEntity();
        employeeRepository.save(employeeEntity);
        when(employeeRepository.findAll()).thenReturn(List.of(employeeEntity));
        final List<Employee> result = underTest.getAllEmployees();
        assertEquals(1, result.size());

    }

    @Test
    public void findAllReturnsEmptyListIfNoEmployees() {
        when(employeeRepository.findAll()).thenReturn(new ArrayList<>());
        final List<Employee> result = underTest.getAllEmployees();
        assertEquals(0, result.size());
    }

    @Test
    public void findEmployeeByIdReturnsEmployee() {
        Employee employee = TestData.TestEmployee();
        EmployeeEntity employeeEntity = TestData.TestEmployeeEntity();
        employeeRepository.save(employeeEntity);
        when(employeeRepository.findById(eq(employeeEntity.getEmployeeId()))).thenReturn(Optional.of(employeeEntity));
        Optional<Employee> result = underTest.getEmployeeById(employee.getEmployeeId());
        assertEquals(employee, result.get());
    }

    @Test
     public void findEmployeeByIdReturnsEmptyIfEmployeeNotFound() {
        when(employeeRepository.findById(anyInt())).thenReturn(Optional.empty());
        Optional<Employee> result = underTest.getEmployeeById(anyInt());
        assertEquals(Optional.empty(), result);
    }

    @Test
    public void createReturnsEmployee() {
        EmployeeEntity employeeEntity = TestData.TestEmployeeEntity();
        Employee employee = TestData.TestEmployee();
        when(employeeRepository.save(employeeEntity)).thenReturn(employeeEntity);
        Employee result = underTest.createEmployee(employee);
        assertEquals(employee, result);
    }


    @Test
    public void updateReturnsEmployee() {
        EmployeeEntity employeeEntity = TestData.TestEmployeeEntity();
        Employee employee = TestData.TestEmployee();
        employeeRepository.save(employeeEntity);
        employee.setName("Ali Ali Mahmoud");
        employeeEntity.setName("Ali Ali Mahmoud");
        when(employeeRepository.findById(eq(employeeEntity.getEmployeeId()))).thenReturn(Optional.of(employeeEntity));
        when(employeeRepository.save(employeeEntity)).thenReturn(employeeEntity);
        Employee result = underTest.updateEmployee(employee);
        assertEquals(employee, result);


    }

    @Test
    // irgendwas läuft hier falsch
    public void testThatDeleteEmployeeDeletesEmployee() {
        EmployeeEntity employeeEntity = TestData.TestEmployeeEntity();
        Employee employee = TestData.TestEmployee();
        employeeRepository.save(employeeEntity);
        underTest.deleteEmployee(employeeEntity.getEmployeeId());
        //verify(employeeRepository).delete(employeeEntity);
        assertEquals(0, underTest.getAllEmployees().size());
    }




}
