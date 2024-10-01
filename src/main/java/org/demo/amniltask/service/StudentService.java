package org.demo.amniltask.service;

import org.demo.amniltask.model.Student;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {

    public List<Student> getAllStudents();
    public Student getStudentById(int id);
    public void addStudent(Student student);
    public void updateStudent(Student student);
    public void deleteStudentById(int id);

}
