package com.alvirg.example.student;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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


        // to be called only once
        Mockito.verify(studentMapper, Mockito.times(1)).toStudent(studentDto);
        Mockito.verify(studentRepository, Mockito.times(1)).save(student);
        Mockito.verify(studentMapper, Mockito.times(1)).toStudentResponseDto(savedStudent);

    }

    @Test
    public void shouldReturnAllStudent(){

        // Given
        // create a list and add students
        List<Student> students = new ArrayList<>();
        students.add(new Student(
                "Jane",
                "Smith",
                "markcollins@gmail.com",
                20));

        // Mock the calls: Mock every call that uses another dependency
        Mockito.when(studentRepository.findAll()).thenReturn(students);
        Mockito.when(studentMapper.toStudentResponseDto(ArgumentMatchers.any(Student.class)))
                .thenReturn(new StudentResponseDto(
                        "Jane",
                        "Smith",
                        "markcollins@gmail.com"));

        // When
        List<StudentResponseDto> studentResponseDtos = studentService.findAllStudent();

        // Then
        Assertions.assertEquals(students.size(), studentResponseDtos.size());

        Mockito.verify(studentRepository, Mockito.times(1)).findAll();


    }

    @Test
    public void shouldReturnStudentById(){
        // Given
        Integer studentId = 1;
        Student student = new Student(
                "Jane",
                "Smith",
                "markcollins@gmail.com",
                20
        );


        // Mock call
        Mockito.when(studentRepository.findById(studentId))
                .thenReturn(Optional.of(student));
        Mockito.when(studentMapper.toStudentResponseDto(ArgumentMatchers.any(Student.class)))
                .thenReturn(new StudentResponseDto(
                        "Jane",
                        "Smith",
                        "markcollins@gmail.com"));

        // When

        StudentResponseDto studentResponseDto = studentService.findStudentById(studentId);

        // Then
        Assertions.assertEquals(studentResponseDto.firstname(), student.getFirstname());
        Assertions.assertEquals(studentResponseDto.lastname(), student.getLastname());
        Assertions.assertEquals(studentResponseDto.email(), student.getEmail());

        Mockito.verify(studentRepository, Mockito.times(1)).findById(studentId);

    }

    @Test
    public void shouldReturnStudentByName(){

        // Given
        String studentName = "Jane";


//         create a list and add students
        List<Student> students = new ArrayList<>();
        students.add(new Student(
                "Jane",
                "Smith",
                "markcollins@gmail.com",
                20));

        Mockito.when(studentRepository.findAllByFirstnameContainingIgnoreCase(studentName)).thenReturn(students);
        Mockito.when(studentMapper.toStudentResponseDto(ArgumentMatchers.any(Student.class)))
                .thenReturn(new StudentResponseDto(
                        "Jane",
                        "Smith",
                        "markcollins@gmail.com"));

        // When
        List<StudentResponseDto> studentResponseDto = studentService.findStudentByName(studentName);

        // Then
        Assertions.assertEquals(studentResponseDto.size(), students.size());

        Mockito.verify(studentRepository, Mockito.times(1)).findAllByFirstnameContainingIgnoreCase(studentName);

    }

    @Test
    public void shouldDeleteAStudentById(){

        // Given
        Integer studentId = 1;

        Student student = new Student(
                "Jane",
                "Smith",
                "markcollins@gmail.com",
                20
        );

        // Mock call
        Mockito.when(studentRepository.findById(studentId))
                .thenReturn(Optional.of(student));

        // When
        studentService.deleteById(studentId);

        // Then

        Mockito.verify(studentRepository, Mockito.times(1)).deleteById(studentId);


    }
}