package com.spring.jpa.chap01_basic.repository;

import static com.spring.jpa.chap01_basic.entity.Product.Category.*;
import com.spring.jpa.chap01_basic.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Id;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional // 데이터베이스의 상태를 변화시키기 위해서 수행하는 작업의 단위
@Rollback(false) //테이블을 test 이전으로 돌릴지의 여부
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @BeforeEach //테스트 돌리기 전에 실행
    void insertDummyData() {
        //given
        Product p1 = Product.builder()
                .name("아이폰")
                .category(ELECTRONIC)
                .price(1200000)
                .build();
        Product p2 = Product.builder()
                .name("탕수육")
                .category(FOOD)
                .price(20000)
                .build();
        Product p3 = Product.builder()
                .name("구두")
                .category(FASHION)
                .price(300000)
                .build();
        Product p4 = Product.builder()
                .name("쓰레기")
                .category(FOOD)
                .build();

        Product saved1 = productRepository.save(p1);
        Product saved2 = productRepository.save(p2);
        Product saved3 = productRepository.save(p3);
        Product saved4 = productRepository.save(p4);
    }

    @Test
    @DisplayName("상품 4개를 데이터베이스에 저장해야 한다.")
    void testSave() {
        Product product = Product.builder()
                .name("정장")
                .price(1000000)
                .category(FASHION)
                .build();


        Product saved = productRepository.save(product);

        assertNotNull(saved);
    }

    @Test
    @DisplayName("id가 2번인 데이터를 삭제해야한다.")
    void testRemove() {
        //given
        long id = 2L;
        //when
        productRepository.deleteById(id);
        //then
    }
    
    @Test
    @DisplayName("상품 전체조회를 하면 상품의 개수가 4개여야 한다.")
    void textFindAll() {
        //given
        
        //when
        List<Product> products = productRepository.findAll();
        //then
        products.forEach(System.out::println);

        assertEquals(4, products.size()); // 조회결과가 4개여야 한다.
    }

    @Test
    @DisplayName("3번 상품을 조회하면 상품명이 '구두'여야 한다.")
    void testFindOne() {
        //given
        long id = 3L; // id = 3
        //when
        Optional<Product> product = productRepository.findById(id); 
        //optional 클래스란? null 일수도 있는 객체 를 감싸는 일종의 wrapper클래스

        //then
        product.ifPresent( p ->  {
            assertEquals("구두", p.getName());
        }); //프로덕트가 존재한다면

        Product foundProduct = product.get(); //3번이 널이 아니여야 통과
        assertNotNull(foundProduct);
    }

    @Test
    @DisplayName("2번 상품의 이름과 가격을 변경해야 한다.")
    void testModify() {
        //given
        long id = 2L;
        String newName = "짜장면";
        int newPrice = 6000;

        //when
        //jpa에서 update는 따로 메서드를 지원하지 않고
        //조회를 한 후 setter로 변경하면 자동으로 update문이 나간다.
        //변경 후 다시 save를 호출하세요.
        Optional<Product> product = productRepository.findById(id);
        product.ifPresent(p -> {  // p : 변수선언   product가 존재한다면~~
            p.setName(newName);
            p.setPrice(newPrice);

            productRepository.save(p);
        });

        //then
        assertTrue(product.isPresent());

        Product p = product.get();
        assertEquals("짜장면", p.getName()); //업데이트 되서 짜장면을 바뀌었는지 확인
    }


}