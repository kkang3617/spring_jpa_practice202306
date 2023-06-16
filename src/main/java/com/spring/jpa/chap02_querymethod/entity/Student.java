package com.spring.jpa.chap02_querymethod.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
@Setter // 실무적 측면에서 setter는 신중하게 만들 것.
@Getter
@ToString @EqualsAndHashCode(of = "id") // id 같으면 같은 객체로 인식.
@NoArgsConstructor
@AllArgsConstructor
@Builder // 객체를 대신 생성해줌
@Entity
@Table(name = "tbl_student")
public class Student {
    @Id
    @Column(name = "stu_id")
    @GeneratedValue(generator = "uid")
    @GenericGenerator(strategy = "uuid", name = "uid")
    private String id;

    @Column(name = "stu_name", nullable = false)
    private String name;

    private String city;
    private String major;



}
