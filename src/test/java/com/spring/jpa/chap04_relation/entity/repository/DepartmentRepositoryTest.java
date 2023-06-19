package com.spring.jpa.chap04_relation.entity.repository;

import com.spring.jpa.chap04_relation.entity.Department;
import com.spring.jpa.chap04_relation.entity.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
@Transactional
@Rollback(false)
class DepartmentRepositoryTest {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    EntityManager entityManager; // 커밋을 원하는 위치에서 해줄 수 있다.

    @Test
    @DisplayName("특정 부서를 조회하면 해당 부서원들도 함께 조회되어야 한다.")
    void testFindDept() {
        //given
        long id = 2L;
        //when
        Department department = departmentRepository.findById(id)
                .orElseThrow();
        
        //then
        System.out.println("\n\n\n");
        System.out.println("department = " + department);
        System.out.println("department.getEmployees() = " + department.getEmployees());
        System.out.println("\n\n\n");
    }

    @Test
    @DisplayName("Lazy 로딩과 Eager 로딩의 차이")
    void testLazyAndEager() {
        // 3번 사원을 조회하고 싶은데, 굳이 부서 정보는 필요없다
        //given
        long id = 3L;
        //when
        Employee employee
                =employeeRepository.findById(id).orElseThrow();
        //then
        System.out.println("\n\n\n");
        System.out.println("employee = " + employee);
        System.out.println("\n\n\n");
    }

    @Test
    @DisplayName("양뱡향 연관관계에서 연관데이터의 수정")
    void testChangeDept() {
        // 3번 사원의 부서를 2번 부서에서 1번 부서로 변경하도록 한다
        //given
        long id = 3L;
        //when
        Employee foundEmp
                = employeeRepository.findById(id).orElseThrow();

        Department newDept
                = departmentRepository.findById(1L).orElseThrow();

        //사원의 부서정보를 업데이트 하면서, 부서에 대한 정보도 같이 업데이트
        foundEmp.setDepartment(newDept);

        employeeRepository.save(foundEmp); //마지막으로 save를 진행하기 때문에

        // 변경 감지(더티 체크) 후 변경된 내용을 DB에 반영하는 역할을 한다.
//        entityManager.flush();
//        entityManager.clear();
        //then
        //1번 부서 정보를 조회해서 모든 사원을 보겠다.
        Department foundDept = departmentRepository.findById(1L).orElseThrow();

        System.out.println("\n\n\n");
        foundDept.getEmployees().forEach(System.out::println);
        System.out.println("\n\n\n");

    }
    @Test
    @DisplayName("N + 1 문제 발생 예시")
    void testNPlus1Ex() {
        //given
        List<Department> departments = departmentRepository.findAll();

        //when
        departments.forEach(dept -> {
            System.out.println("\n\n=========== 사원 리스트 ==========");

            List<Employee> employees = dept.getEmployees();
            System.out.println("employees = " + employees);

            System.out.println("\n\n");

        });
    }
            //then

    @Test
    @DisplayName("N + 1 문제 해결 예시")
    void testNPlus1Solution() {
        //given
        List<Department> departments
                = departmentRepository.findAllIncludeEmployees();

        //when
        departments.forEach(dept -> {
            System.out.println("\n\n=========== 사원 리스트 ==========");

            List<Employee> employees = dept.getEmployees();
            System.out.println("employees = " + employees);

            System.out.println("\n\n");

        });

    }
    
}