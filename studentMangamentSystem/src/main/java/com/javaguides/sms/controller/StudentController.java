package com.javaguides.sms.controller;




import com.javaguides.sms.domain.StudentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.javaguides.sms.services.StudentService;

import java.util.List;
import java.util.Optional;

@Controller
public class StudentController {

    private StudentService studentService;
    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    // hanlder method to handle list students and return mode and view
    @GetMapping(value = "/students")
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "students"; // it returns a view
    }

    @GetMapping(value = "students/new")
    public String createStudentForm(Model model) {
        // create StudentEntityObj to hold Students from data
        StudentEntity studentEntity = new StudentEntity();
        model.addAttribute("student", studentEntity);
        return "create_student";

    }

    @PostMapping("/students")
    public String saveStudent(@ModelAttribute("student") StudentEntity studentEntity) {
        studentService.addStudent(studentEntity);
        return "redirect:/students";
    }

    @GetMapping(value = "student/update/{id}")
    public String updateStudentForm(@PathVariable final long id, Model model) {
        Optional<StudentEntity> studentOptional = studentService.getStudentById(id);
        //if (studentOptional.isPresent()) {
           // model.addAttribute("student", studentOptional.get());
        model.addAttribute("student", studentOptional.get());
        return "update_student";
        //}

        //return createStudentForm(model);
    }

    @PostMapping("/students/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute("student") StudentEntity student, Model model) {
        // get student from database by id
        student.setId(id);
        studentService.updateStudent(student);

        return "redirect:/students";
    }


    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable long id) {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }












}
