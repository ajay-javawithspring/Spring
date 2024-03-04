package com.stone.springbootrestblog.repository;

import com.stone.springbootrestblog.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
