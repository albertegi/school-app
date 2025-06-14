package com.alvirg.example.student;

import com.alvirg.example.school.School;
import org.springframework.stereotype.Service;

@Service
public class StudentMapper {

    // We need to convert or transform studentDto object
    // into student object
    // So we create a method that will receive exactly studentDto
    // we can use constructor or getter and setters. we used setters here

    public Student toStudent(StudentDto studentDto){

        if(studentDto == null){
            throw new NullPointerException("The student dto should not be null");
        }

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
