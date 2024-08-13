package com.javaguides.sms.service.impl;

import com.javaguides.sms.domain.StudentEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.javaguides.sms.repositories.StudentRepository;
import com.javaguides.sms.services.Impl.StudentServiceImpl;

import java.util.List;
import java.util.Optional;

import static com.javaguides.sms.TestData.stdTest;
import static javax.management.Query.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
public class StudentServiceImplTests {
    @Mock
    private StudentRepository studentRepository;
    @InjectMocks
    private StudentServiceImpl underTest;


    @Test
    public void testThatFindAllReturnsAllStudents() {
        StudentEntity std= stdTest();
        studentRepository.save(std);
        when(studentRepository.findAll()).thenReturn(List.of(std));
        final List<StudentEntity> result = underTest.getAllStudents();
        assertEquals(1,result.size());
    }

    @Test
    public void testThataddStudentReturnsStudent() {
        StudentEntity std= stdTest();
        underTest.addStudent(std);
        when(studentRepository.save(std)).thenReturn(std);
        final StudentEntity result = underTest.addStudent(std);
        assertEquals(std,result);
    }

    @Test
    public void testThatUpdateStudentReturnsStudent() {
        StudentEntity std= stdTest();
        underTest.addStudent(std);
        std.setFirstName("Updated First Name");
        when(studentRepository.save(std)).thenReturn(std);
        final StudentEntity result = underTest.updateStudent(std);
        assertEquals(std,result);
    }

    @Test
    public void testThatUpdateStudentDoesNotDuplicateStudent(){
        StudentEntity std= stdTest();
        underTest.addStudent(std);
        std.setFirstName("Updated First Name");
        when(studentRepository.findAll()).thenReturn(List.of(std));
        underTest.updateStudent(std);
        assertEquals(1,underTest.getAllStudents().size());
    }

    @Test
    public void testThatFindStudentByIdReturnsEmpty() {
        underTest.addStudent(stdTest());
        when(studentRepository.findById(any())).thenReturn(Optional.empty());
        Optional<StudentEntity> std = underTest.getStudentById(555);
        assertEquals(std, Optional.empty());
    }
}
