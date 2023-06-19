package com.spring.jpa.chap04_relation.entity.repository;

import com.spring.jpa.chap04_relation.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartmentRepository
    extends JpaRepository<Department, Long> {

    @Query("SELECT DISTINCT d From Department d JOIN FETCH d.employees")
    List<Department> findAllIncludeEmployees();
}








