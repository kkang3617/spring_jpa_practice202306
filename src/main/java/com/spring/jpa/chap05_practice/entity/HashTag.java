package com.spring.jpa.chap05_practice.entity;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@ToString(exclude = {"post"})
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_hash_tag")
public class HashTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tage_no")
    private long id;

    private String tagName; //해시태그 이름

    @ManyToOne(fetch = FetchType.LAZY) // hash n : post 1
    @JoinColumn(name = "post_no") //조인할 컬럼명
    private Post post;


}
