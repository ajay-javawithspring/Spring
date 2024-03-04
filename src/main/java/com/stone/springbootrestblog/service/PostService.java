package com.stone.springbootrestblog.service;

import com.stone.springbootrestblog.entity.Post;
import com.stone.springbootrestblog.payload.PostDto;
import com.stone.springbootrestblog.payload.PostResponse;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;


public interface PostService {
    PostDto retrievePostById(long id);
    PostDto createPost(PostDto postDto);
    PostResponse retrieveAllPosts(int pageNo, int pageSize, String sortBy, String sortWay);
    PostDto updatePost(PostDto postDto, long id);
    void deleteById(long id);

    List<PostDto> retrivePostsbyCategory(long categoryId);


}

