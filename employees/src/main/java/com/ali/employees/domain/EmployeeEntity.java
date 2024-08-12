package com.ali.employees.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Employee")
@Builder
public class EmployeeEntity {
    @Id
    private int employeeId;
    private String Name;
    private int salary;
    private int age;

}
