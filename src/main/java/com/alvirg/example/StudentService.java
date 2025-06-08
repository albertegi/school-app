package com.alvirg.example;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final SchoolRepository schoolRepository;

    public StudentService(StudentRepository studentRepository, StudentMapper studentMapper, SchoolRepository schoolRepository) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.schoolRepository = schoolRepository;
    }

    public StudentResponseDto saveStudent (StudentDto studentDto){
        // create a variable student, call the toStudent method
        // pass the variable to the studentRepository save method
        var student = studentMapper.toStudent(studentDto);
        var savedStudent = studentRepository.save(student);
        return studentMapper.toStudentResponseDto(savedStudent);
    }

    public List<Student> findAllStudent(){

        return studentRepository.findAll();
    }

    public Student findStudentById(Integer id){
        return studentRepository.findById(id).orElse(new Student());
    }

    public List<Student>  findStudentByName(String name){
        return studentRepository.findAllByFirstnameContainingIgnoreCase(name);
    }

    public void deleteById(Integer id){
        studentRepository.deleteById(id);
    }

    public StudentResponseDto updateStudentById(Integer id, StudentDto studentDto) {
        return studentRepository.findById(id)
                .map(existingStudent -> {
                    existingStudent.setFirstname(studentDto.firstname());
                    existingStudent.setLastname(studentDto.lastname());
                    existingStudent.setEmail(studentDto.email());

                    var school = new School();
                    school.setId(studentDto.schoolId());
                    existingStudent.setSchool(school);

                    var updatedStudent = studentRepository.save(existingStudent);
                    return studentMapper.toStudentResponseDto(updatedStudent);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found!"));
    }
}
