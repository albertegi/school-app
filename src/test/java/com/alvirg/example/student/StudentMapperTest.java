package com.alvirg.example.student;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class StudentMapperTest {


    private StudentMapper studentMapper;


    @BeforeEach
    void setUp() {
        studentMapper = new StudentMapper();
    }

    @Test
    public void shouldMapStudentDtoToStudent(){
        StudentDto studentDto = new StudentDto(
                "John",
                "Doe",
                "johndoe@gmail.com",
                1);

        Student student = studentMapper.toStudent(studentDto);

        Assertions.assertEquals(studentDto.firstname(), student.getFirstname());
        Assertions.assertEquals(studentDto.lastname(), student.getLastname());
        Assertions.assertEquals(studentDto.email(), student.getEmail());
        Assertions.assertNotNull(student.getSchool());
        Assertions.assertEquals(studentDto.firstname(), student.getFirstname());
        Assertions.assertEquals(studentDto.schoolId(), student.getSchool().getId());
    }

    @Test
    public void shouldMapStudentToStudentResponseDto(){
        // Given
        Student student = new Student(
                "Jane",
                "Smith",
                "markcollins@gmail.com",
                20);

        // When
        StudentResponseDto studentResponseDto = studentMapper.toStudentResponseDto(student);

        // Then
        Assertions.assertEquals(studentResponseDto.firstname(), student.getFirstname());
        Assertions.assertEquals(studentResponseDto.lastname(), student.getLastname());
        Assertions.assertEquals(studentResponseDto.email(), student.getEmail());

    }


    @BeforeAll
    static void beforeAll() {
        System.out.println("Inside the before All method");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("Inside the after All method");
    }



    @AfterEach
    void tearDown() {
        System.out.println("Inside the after each method");
    }

    @Test
    public void testMethod1(){
        System.out.println("My first test method");
    }

    @Test
    public void testMethod2(){
        System.out.println("My second test method");
    }


}