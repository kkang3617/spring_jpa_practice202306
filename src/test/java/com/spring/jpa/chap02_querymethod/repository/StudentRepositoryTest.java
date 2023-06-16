package com.spring.jpa.chap02_querymethod.repository;

import com.spring.jpa.chap02_querymethod.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@Rollback(false)
class StudentRepositoryTest {
    @Autowired
    StudentRepository studentRepository;

    @BeforeEach
    void insertData() {
        Student s1 = Student.builder()
                .name("춘식이")
                .city("서울")
                .major("수학과")
                .build();
        Student s2 = Student.builder()
                .name("라이언")
                .city("경기")
                .major("국문과")
                .build();
        Student s3 = Student.builder()
                .name("대길이")
                .city("한양도성")
                .major("수학과")
                .build();

        studentRepository.save(s1);
        studentRepository.save(s2);
        studentRepository.save(s3);
    }
    
    @Test
    @DisplayName("이름이 춘식이인 학생의 정보를 조회해야 한다")
    void testFindByName() {
        //given
        String name = "춘식이";
        //when
        List<Student> students = studentRepository.findByName(name);

        //then
        assertEquals(1, students.size());

        System.out.println("name = " + name);

    }

    @Test
    @DisplayName("testFindByCityAndMajor")
    void testFindByCityAndMajor() {
        //given
        String city = "경기";
        String major = "국문과";

        //when
        List<Student> students = studentRepository.findByCityAndMajor(city, major);

        //then
        assertEquals(1, students.size());
        assertEquals("라이언", students.get(0).getName());

        System.out.println("students.get(0) = " + students.get(0));


    }

    @Test
    @DisplayName("findByMajorContaining")
    void testfindByMajorContaining() {
        //given
        String major = "수학과";

        //when
        List<Student> students = studentRepository.findByMajorContaining(major);

        //then
        assertEquals(2, students.size());

        System.out.println("================");
        students.forEach(System.out::println);
        System.out.println("================");


    }

    @Test
    @DisplayName("testNativeSQL")
    void testNativeSQL() {
        //given
        String name = "대길이";

        //when
        Student student = studentRepository.findNameWithSQL(name);

        //then
        assertEquals("한양도성", student.getCity());

        System.out.println("================");
        System.out.println("student = " + student);
        System.out.println("================");


    }

    @Test
    @DisplayName("testFindCityWithJPQL")
    void testFindCityWithJPQL() {
        //given
        String city = "서울";

        //when
        List<Student> students = studentRepository.getByCityWithJPQL(city);

        //then
        assertEquals("춘식이", students.get(0).getName());

        System.out.println("================");
        System.out.println("student = " + students.get(0));
        System.out.println("================");


    }

    @Test
    @DisplayName("testSearchNameJPQL")
    void testSearchNameJPQL() {
        //given
        String name = "이";
        //when
        List<Student> students = studentRepository.searchByNamesWithJPQL(name);
        //then
        assertEquals(3, students.size());

        System.out.println("\n\n\n");
        students.forEach(System.out::println);
        System.out.println("\n\n\n");

    }

    @Test
    @DisplayName("JPQL로 삭제하기")
    void testDeleteWithJPQL() {
        //given
        String name = "대길이";
        //when
        studentRepository.deleteByNameWithJPQL(name);
        //then
        List<Student> students = studentRepository.findByName(name);

        assertEquals(0, students.size());
    }

}