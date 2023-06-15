package com.spring.jpa.chap01_basic.entity;

import lombok.*;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter @Getter
@ToString @EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    @Column(name = "prod_id")
    private long id;
    @Column(name = "prod_name", nullable = false, length = 30)
    private String name;

    private int price;

    @Enumerated(EnumType.STRING) //enum타입을 String으로 가져온다.
    private Category category;

    @CreationTimestamp
    @Column(updatable = false) // 한번 insert 된후에는 해당컬럼 수정불가능.
    private LocalDateTime createdDate;

    @UpdateTimestamp //업데이트 될때
    private LocalDateTime updatedDate;

    public enum Category {
        FOOD, FASHION, ELECTRONIC
    }

}
