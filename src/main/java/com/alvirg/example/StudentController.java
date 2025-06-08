package com.alvirg.example;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class StudentController {

    private final StudentRepository studentRepository;


    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @PostMapping("/students")
    @ResponseStatus(HttpStatus.CREATED)
    public StudentResponseDto post(@RequestBody StudentDto studentDto){

        // create a variable student, call the toStudent method
        // pass the variable to the studentRepository save method
        var student = toStudent(studentDto);

        var savedStudent = studentRepository.save(student);

        return toStudentResponseDto(savedStudent);
    }

    // We need to convert or transform studentDto object
    // in line 21 into student object
    // So we create a private method in line 32 below it will receive exactly studentDto
    // we can use constructor or getter and setters. we used setters here

    private Student toStudent(StudentDto studentDto){
        Student student = new Student();
        student.setFirstname(studentDto.firstname());
        student.setLastname(studentDto.lastname());
        student.setEmail(studentDto.email());

        // create an object of type school and link it with the student object
        // because schoolId is one of the fields in the StudentDto
        // so the school object is prepared here
        var school = new School();
        school.setId(studentDto.schoolId());

        // assign school object to student
        student.setSchool(school);
        return student;

    }

    private StudentResponseDto toStudentResponseDto(Student student){
        return new StudentResponseDto(
                student.getFirstname(),
                student.getLastname(),
                student.getEmail());
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
