package com.spring.jpa.chap04_relation.entity;

import lombok.*;

import javax.persistence.*;

@Getter @Setter
@ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_emp")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    private long id;

    @Column(name = "emp_name", nullable = false)
    private String name;

    @ManyToOne  //employee가 many department가 1
    @JoinColumn(name = "dept_id")
    private Department department;


}
