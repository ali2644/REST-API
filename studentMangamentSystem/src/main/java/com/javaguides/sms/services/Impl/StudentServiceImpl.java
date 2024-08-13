package com.javaguides.sms.services.Impl;

import com.javaguides.sms.domain.StudentEntity;
import org.springframework.stereotype.Service;
import com.javaguides.sms.repositories.StudentRepository;
import com.javaguides.sms.services.StudentService;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    private StudentRepository studentRepository;

    //@Autowired--> if it has only one Constructor, we do not really need an Autowired annotation
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    @Override
    public List<StudentEntity> getAllStudents() {
        List<StudentEntity> students = studentRepository.findAll();
        return students;
    }

    @Override
    public Optional<StudentEntity> getStudentById(long id) {
        return studentRepository.findById(id);

    }

    @Override
    public StudentEntity addStudent(StudentEntity student) {
        return studentRepository.save(student);
    }

    @Override
    public StudentEntity updateStudent(StudentEntity student) {
       Optional<StudentEntity> Optionalstd= studentRepository.findById(student.getId());
       if(Optionalstd.isPresent()) {
           StudentEntity std = studentRepository.findById(student.getId()).get();
           std.setFirstName(student.getFirstName());
           std.setLastName(student.getLastName());
           std.setEmail(student.getEmail());
           return studentRepository.save(std);
       }
       return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }


}
