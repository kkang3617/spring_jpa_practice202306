package com.spring.jpa.chap04_relation.entity;

import lombok.*;

import javax.persistence.*;

@Getter @Setter
// jpa 연관관계 매핑에서 연관관계 데이터는 ToString 에서 제외해야 한다.
@ToString(exclude = {"department"}) // exclude : toString 에서 제외하겠다
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

    //EAGER: 항상 무조건 조인을 수행
    //LAZY: 필요한 경우에만 조인을 수행 (실무)
    @ManyToOne(fetch = FetchType.LAZY)  //employee가 many department가 1  join문 대신해서 선언해놓음.
    @JoinColumn(name = "dept_id")  // join에 사용할 컬럼명.
    private Department department;

    public void setDepartment(Department department) {
        this.department = department;
        department.getEmployees().add(this);
    }
}
