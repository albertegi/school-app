package com.alvirg.example;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class StudentController {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;


    public StudentController(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    @PostMapping("/students")
    @ResponseStatus(HttpStatus.CREATED)
    public StudentResponseDto post(@RequestBody StudentDto studentDto){

        // create a variable student, call the toStudent method
        // pass the variable to the studentRepository save method
        var student = studentMapper.toStudent(studentDto);

        var savedStudent = studentRepository.save(student);

        return studentMapper.toStudentResponseDto(savedStudent);
    }



    @GetMapping("/students")
    public List<Student> findAllStudent(){
        return studentRepository.findAll();
    }

    @GetMapping("/students/{student-id}")
    public Student findStudentById(@PathVariable("student-id") Integer id){
        return studentRepository.findById(id).orElse(new Student());
    }

    @GetMapping("/students/search/{student-name}")
    public List<Student>  findStudentByName(@PathVariable("student-name") String name){
        return studentRepository.findAllByFirstnameContainingIgnoreCase(name);
    }

    @DeleteMapping("/students/{student-id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("student-id") Integer id){
        studentRepository.deleteById(id);
    }

    @PutMapping("/students/{student-id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Student update(@RequestBody Student student, @PathVariable("student-id") Integer id) {
        return studentRepository.findById(id)
                .map(existingStudent -> {
                    existingStudent.setFirstname(student.getFirstname());
                    existingStudent.setLastname(student.getLastname());
                    existingStudent.setEmail(student.getEmail());
                    existingStudent.setAge(student.getAge());
                    return studentRepository.save(existingStudent);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
    }



}
