package com.stone.springbootrestblog.repository;

import com.stone.springbootrestblog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByCategoryId(long categoryId);

}
