package com.spring.jpa.chap05_practice.repository;

import com.spring.jpa.chap05_practice.entity.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashTagRepository
    extends JpaRepository<HashTag, Long> {
}
