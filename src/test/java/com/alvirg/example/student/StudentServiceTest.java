package com.alvirg.example.student;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentServiceTest {

    // Which service we want to test? create an object of that service
    @InjectMocks
    private StudentService studentService;

    // declare the dependencies

    @Mock
    StudentRepository studentRepository;

    @Mock
    StudentMapper studentMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldSuccessfullySaveAStudent(){
        // Given

        StudentDto studentDto = new StudentDto(
                "John",
                "Doe",
                "johndoe@gmail.com",
                1);

        Student student = new Student(
                "Jane",
                "Doe",
                "janedoe@gmail.com",
                20);

        Student savedStudent = new Student(
                "Jane",
                "Doe",
                "markcollins@gmail.com",
                20);
        savedStudent.setId(1);

        // Mock the calls: Mock every call that uses another dependency
        Mockito.when(studentMapper.toStudent(studentDto)).thenReturn(student);
        Mockito.when(studentRepository.save(student)).thenReturn(savedStudent);
        Mockito.when(studentMapper.toStudentResponseDto(savedStudent))
                .thenReturn(new StudentResponseDto(
                        "John",
                        "Doe",
                        "johndoe@gmail.com"));

        // When
        StudentResponseDto studentResponseDto = studentService.saveStudent(studentDto);

        // Then
        Assertions.assertEquals(studentDto.firstname(), studentResponseDto.firstname());
        Assertions.assertEquals(studentDto.lastname(), studentResponseDto.lastname());
        Assertions.assertEquals(studentDto.email(), studentResponseDto.email());
    }
}