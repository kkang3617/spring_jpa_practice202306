package com.spring.jpa.chap05_practice.service;

import com.spring.jpa.chap05_practice.dto.PageDTO;
import com.spring.jpa.chap05_practice.dto.PageResponseDTO;
import com.spring.jpa.chap05_practice.dto.PostDetailResponseDTO;
import com.spring.jpa.chap05_practice.dto.PostListResponseDTO;
import com.spring.jpa.chap05_practice.entity.Post;
import com.spring.jpa.chap05_practice.repository.HashTagRepository;
import com.spring.jpa.chap05_practice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional // JPA 레파지토리는 트랜잭션 단위로 동작하기 때문에 작성해 줘야함!
public class PostService {

    private final PostRepository postRepository;
    private final HashTagRepository hashTagRepository;


    public PostListResponseDTO getPosts(PageDTO dto) {

        //pageable 객체 생성
        Pageable pageable = PageRequest.of(
            dto.getPage() - 1,
                dto.getSize(),
                Sort.by("createDate").descending()

        );

        //데이터베이스에서 게시물 목록 조회
        Page<Post> posts = postRepository.findAll(pageable);
        
        //게시물 정보만 꺼내기
        List<Post> postList = posts.getContent();

        //엔터티 객체를 DTO 객체로 변환한 결과 리스트.
        List<PostDetailResponseDTO> detailList
                = postList.stream()
                          .map(post -> new PostDetailResponseDTO(post))
                          .collect(Collectors.toList());


        //DB에서 조회한 정보(ENTITY)를 JSON 형태에 맞는 DTO로 변환
        PostListResponseDTO responseDTO = PostListResponseDTO.builder()
                .count(detailList.size()) //총 게시물 수가 아니라 조회된 게시물 수
                .pageInfo(new PageResponseDTO(posts)) //생성자에게 Page정보가 담긴 객체를 그대로 전달.
                .posts(detailList)
                .build();

        return responseDTO;
    }
}
