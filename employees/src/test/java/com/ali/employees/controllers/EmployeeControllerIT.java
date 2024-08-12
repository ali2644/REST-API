package com.ali.employees.controllers;


import com.ali.employees.TestData;
import com.ali.employees.controller.EmployeeController;
import com.ali.employees.domain.Employee;
import com.ali.employees.services.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import  org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class EmployeeControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EmployeeService employeeService;

    @Test
    public void testThatReturnsHTTP200WhenNoEmployeeFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/employee"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

     @Test
    public void testThatReturnsHTTP200WhenEmployeeFound() throws Exception {
        final Employee employee = TestData.TestEmployee();
        employeeService.createEmployee(employee);
        mockMvc.perform(MockMvcRequestBuilders.get("/employee"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employeeId").value(employee.getEmployeeId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].salary").value(employee.getSalary()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(employee.getAge()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(employee.getName()));

    }


    @Test
    public void testCreateEmployeeThatReturnsHTTP201WhenEmployeeCreated() throws Exception {
        final Employee employee = TestData.TestEmployee();
        final ObjectMapper objectMapper = new ObjectMapper();
        final String json = objectMapper.writeValueAsString(employee);

        employeeService.createEmployee(employee);
        mockMvc.perform(MockMvcRequestBuilders.post("/employee/create/"+employee.getEmployeeId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeId").value(employee.getEmployeeId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(employee.getSalary()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(employee.getAge()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(employee.getName()));
    }

    @Test
    public void testFindByIdThatReturnsHTTP302WhenEmployeeFound() throws Exception {
        final Employee employee = TestData.TestEmployee();
        employeeService.createEmployee(employee);
        mockMvc.perform(MockMvcRequestBuilders.get("/employee/"+employee.getEmployeeId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeId").value(employee.getEmployeeId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(employee.getSalary()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(employee.getAge()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(employee.getName()));
    }

    @Test
    public void testFindByIdThatReturnsHTTP404WhenEmployeeNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/employee/999"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testupdateEmployeeThatReturnsHTTP202WhenEmployeeUpdated() throws Exception {
        Employee employee = TestData.TestEmployee();
        employeeService.createEmployee(employee);
        employee.setAge(25);
        employee.setSalary(5000);
        final ObjectMapper objectMapper = new ObjectMapper();
        final String json = objectMapper.writeValueAsString(employee);

        mockMvc.perform(MockMvcRequestBuilders.put("/employee/update/"+employee.getEmployeeId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeId").value(employee.getEmployeeId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(employee.getSalary()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(employee.getAge()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(employee.getName()));
    }


    @Test
    public void testThatDeleteEmployeeReturnsHTTP202WhenEmployeeDeleted() throws Exception {
        final Employee employee = TestData.TestEmployee();
        employeeService.createEmployee(employee);
        mockMvc.perform(MockMvcRequestBuilders.delete("/employee/delete/"+employee.getEmployeeId()))
                .andExpect(MockMvcResultMatchers.status().isAccepted());
    }

    @Test
    public void testThatDeleteEmployeeThatReturnsHTTP404WhenEmployeeNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/employee/delete/999"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


}
