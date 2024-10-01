package org.demo.amniltask.controller;

import org.demo.amniltask.model.Student;
import org.demo.amniltask.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class StudentController {

    @Autowired
    public StudentService studentService;

    @GetMapping("/")
    public String index() {
        return "Index";
    }

    @GetMapping("admin/students")
    public String students(Model model) {
        List<Student> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        return "StudentPage";
    }

    @GetMapping("/admin/student/create")
    public String addStudent(Model model) {
        model.addAttribute("student", new Student());
        return "AddStudent";
    }

    @PostMapping("/admin/student/create")
    public String addStudent(@ModelAttribute("student") Student student, Model model) {
        try{
            studentService.addStudent(student);
            return "redirect:/admin/students";
        }
        catch(Exception e){
            model.addAttribute("error", e.getMessage());
            return "AddStudent";
        }
    }

    @GetMapping("/admin/student/edit/{id}")
    public String editStudent(@PathVariable("id") int id, Model model) {
        model.addAttribute("student", studentService.getStudentById(id));
        return "EditStudent";
    }

    @PostMapping("/admin/student/edit/{id}")
    public String editStudent(@ModelAttribute("student") Student student, Model model, @PathVariable int id) {

            try{
                Student student1 = studentService.getStudentById(id);

                if(student1 != null){
                    student1.setName(student.getName());
                    student1.setAddress(student.getAddress());
                    student1.setEmail(student.getEmail());
                    student1.setPhone(student.getPhone());

                    studentService.updateStudent(student1);
                    return "redirect:/admin/students";
                }
                model.addAttribute("error", "Student not found");
                return "EditStudent";
            } catch (Exception e) {
                model.addAttribute("error", e.getMessage());
                return "EditStudent";
            }
        }

        @GetMapping("/admin/student/delete/{id}")
    public String deleteStudent(@PathVariable("id") int id, Model model) {
        studentService.deleteStudentById(id);
        return "redirect:/admin/students";
    }

    @GetMapping("/studentpage")
    public String studentPage(Model model) {
        List<Student> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        return "ViewStudentPage";
    }
}
