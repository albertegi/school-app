package com.alvirg.example;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/students")
    @ResponseStatus(HttpStatus.CREATED)
    public StudentResponseDto saveStudent(@RequestBody StudentDto studentDto){
        return this.studentService.saveStudent(studentDto);
    }



    @GetMapping("/students")
    public List<Student> findAllStudent(){

        return studentService.findAllStudent();
    }

    @GetMapping("/students/{student-id}")
    public Student findStudentById(@PathVariable("student-id") Integer id){
        return studentService.findStudentById(id);
    }

    @GetMapping("/students/search/{student-name}")
    public List<Student>  findStudentByName(@PathVariable("student-name") String name){
        return studentService.findStudentByName(name);
    }

    @DeleteMapping("/students/{student-id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("student-id") Integer id){

        studentService.deleteById(id);
    }

    @PutMapping("/students/{student-id}")
    @ResponseStatus(HttpStatus.OK)
    public StudentResponseDto update(@RequestBody StudentDto studentDto, @PathVariable("student-id") Integer id) {
        return studentService.updateStudentById(id, studentDto);
    }



}
