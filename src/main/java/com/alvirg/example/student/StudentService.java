package com.alvirg.example.student;

import com.alvirg.example.school.School;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public StudentService(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    public StudentResponseDto saveStudent (StudentDto studentDto){
        // create a variable student, call the toStudent method
        // pass the variable to the studentRepository save method
        var student = studentMapper.toStudent(studentDto);
        var savedStudent = studentRepository.save(student);
        return studentMapper.toStudentResponseDto(savedStudent);
    }

    public List<StudentResponseDto> findAllStudent(){
        return studentRepository.findAll()
                .stream()
                .map(studentMapper::toStudentResponseDto)
                .collect(Collectors.toList());
    }

    public StudentResponseDto findStudentById(Integer id){
        return studentRepository.findById(id)
                .map(studentMapper::toStudentResponseDto)
                .orElse(null);
    }

    public List<StudentResponseDto>  findStudentByName(String name){
        return studentRepository.findAllByFirstnameContainingIgnoreCase(name)
                .stream()
                .map(studentMapper::toStudentResponseDto)
                .collect(Collectors.toList());
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
