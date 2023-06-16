package com.spring.jpa.chap04_relation.entity.repository;

import com.spring.jpa.chap04_relation.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository
    extends JpaRepository<Employee, Long> {

}
