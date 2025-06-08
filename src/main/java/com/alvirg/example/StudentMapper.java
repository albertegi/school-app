package com.alvirg.example;

import org.springframework.stereotype.Service;

@Service
public class StudentMapper {

    // We need to convert or transform studentDto object
    // in line 21 into student object
    // So we create a private method in line 32 below it will receive exactly studentDto
    // we can use constructor or getter and setters. we used setters here

    public Student toStudent(StudentDto studentDto){
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

    public StudentResponseDto toStudentResponseDto(Student student){
        return new StudentResponseDto(
                student.getFirstname(),
                student.getLastname(),
                student.getEmail());
    }


}
