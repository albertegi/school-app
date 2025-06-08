package com.alvirg.example;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class StudentController {

    private final StudentService studentService;
    private final StudentMapper studentMapper;

    public StudentController(StudentService studentService, StudentMapper studentMapper) {
        this.studentService = studentService;
        this.studentMapper = studentMapper;
    }

    @PostMapping("/students")
    @ResponseStatus(HttpStatus.CREATED)
    public StudentResponseDto saveStudent(@RequestBody StudentDto studentDto){
        return this.studentService.saveStudent(studentDto);
    }



    @GetMapping("/students")
    public List<StudentResponseDto> findAllStudent(){

        return studentService.findAllStudent();
    }

    @GetMapping("/students/{student-id}")
    public StudentResponseDto findStudentById(@PathVariable("student-id") Integer id){
        return studentService.findStudentById(id);
    }

    @GetMapping("/students/search/{student-name}")
    public List<StudentResponseDto>  findStudentByName(@PathVariable("student-name") String name){
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
