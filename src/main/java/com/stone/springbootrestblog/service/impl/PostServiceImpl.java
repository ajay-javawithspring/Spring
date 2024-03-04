package com.stone.springbootrestblog.service.impl;

import com.stone.springbootrestblog.entity.Category;
import com.stone.springbootrestblog.entity.Post;
import com.stone.springbootrestblog.exception.ResourceNotFoundException;
import com.stone.springbootrestblog.payload.PostDto;
import com.stone.springbootrestblog.payload.PostResponse;
import com.stone.springbootrestblog.repository.CategoryRepository;
import com.stone.springbootrestblog.repository.PostRepository;
import com.stone.springbootrestblog.service.PostService;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@NoArgsConstructor
@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    private ModelMapper modelMapper;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    public PostServiceImpl(ModelMapper modelMapper){
        this.modelMapper=modelMapper;
    }

    @Override
    public PostDto retrievePostById(long id) {
        Post post = getPost(id);
        return mapToDto(post);
    }

    private Post getPost(long id) {
        return this.postRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("post", "id", id));
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        Category category = getCategory(postDto);

        Post post = mapToEntity(postDto);
        post.setCategory(category);
        Post savedPost = postRepository.save(post);

        return mapToDto(savedPost);
    }

    private Category getCategory(PostDto postDto) {
        return categoryRepository.findById(postDto.getCategoryId()).orElseThrow(
                ()-> new ResourceNotFoundException("Category", "Id", postDto.getCategoryId())
        );
    }

    private PostDto mapToDto(Post savedPost) {

        return modelMapper.map(savedPost, PostDto.class);
    }

    private Post mapToEntity(PostDto postDto) {

        return modelMapper.map(postDto, Post.class);
    }

    @Override
    public PostResponse retrieveAllPosts(int pageNo, int pageSize, String sortBy, String sortWay) {

        Sort sort = sortWay.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable =  PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageable);
        List<Post> listOfPosts = posts.getContent();
        List<PostDto> allPosts = listOfPosts.stream().map(this :: mapToDto).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(allPosts);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getNumberOfElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }


    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        
        Category category = getCategory(postDto);

        Post post = getPost(id);

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        post.setCategory(category);

        return mapToDto(post);
    }

    @Override
    public void deleteById(long id) {
        postRepository.deleteById(id);
    }

    @Override
    public List<PostDto> retrivePostsbyCategory(long categoryId) {

        categoryRepository.findById(categoryId).orElseThrow(
                ()-> new ResourceNotFoundException("Category", "id", categoryId));

        return
                postRepository.findByCategoryId(categoryId)
                        .stream()
                                .map((post) -> modelMapper.map(post, PostDto.class))
                                       .collect(Collectors.toList());
    }
}
