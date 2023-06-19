package com.spring.jpa.chap04_relation.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
// jpa 연관관계 매핑에서 연관관계 데이터는 ToString 에서 제외해야 한다.
@ToString(exclude = {"employees"}) // exclude : toString 에서 제외하겠다
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_dept")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dept_id")
    private long id;

    @Column(name = "dept_name", nullable = false)
    private String name;

    // 양방향 매핑에서는 상대방 엔터티의 갱신에 관여할 수 없다.
    // 단순히 읽기전용 (조회)으로만 사용해야 한다.
    // mappedBy에는 상대방 엔터티의 조인이 되는 필드명을 작성.
    @OneToMany(mappedBy = "department")  // dept 1 : employee M
    private List<Employee> employees = new ArrayList<>(); //초기화가 필요하다. arraylist (NPE 방지)



}













