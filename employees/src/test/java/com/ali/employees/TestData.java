package com.ali.employees;

import com.ali.employees.domain.Employee;
import com.ali.employees.domain.EmployeeEntity;

public class TestData {
    public static Employee TestEmployee(){
        return Employee.builder()
               .employeeId(1)
               .Name("Ali Mahmoud")
               .age(22)
               .salary(25000)
               .build();
    }

    public static EmployeeEntity TestEmployeeEntity(){
        return EmployeeEntity.builder()
               .employeeId(1)
               .Name("Ali Mahmoud")
               .age(22)
               .salary(25000)
               .build();
    }
}
