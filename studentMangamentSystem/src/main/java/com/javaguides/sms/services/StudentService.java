package com.javaguides.sms.services;

import com.javaguides.sms.domain.StudentEntity;

import java.util.List;
import java.util.Optional;


public interface StudentService {
    public List<StudentEntity> getAllStudents();

    public Optional<StudentEntity> getStudentById(long id);

    public StudentEntity addStudent(StudentEntity student);

    public StudentEntity updateStudent(StudentEntity student);

    public void deleteStudent(long id);
}
