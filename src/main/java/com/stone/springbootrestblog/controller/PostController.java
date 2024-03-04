package com.stone.springbootrestblog.controller;

import com.stone.springbootrestblog.entity.Post;
import com.stone.springbootrestblog.payload.PostDto;

import com.stone.springbootrestblog.payload.PostResponse;
import com.stone.springbootrestblog.service.PostService;
import com.stone.springbootrestblog.utils.ApplicationConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "REST API for Posts"
)
@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Operation(
            summary = "Create Post API"
            //description = "Create Category API is used to save Category in Database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "CREATED"
    )
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){

        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Retrieve all Posts API"
            //description = "Create Category API is used to save Category in Database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "OK"
    )
    @GetMapping
    public PostResponse getPost(

            @RequestParam(value = "pageNo", defaultValue = ApplicationConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = ApplicationConstants.DEFAULT_SORTBY, required = false) String sortBy,
            @RequestParam(value = "sortWay", defaultValue = ApplicationConstants.DEFAULT_SORTDIR, required = false) String sortWay
    )
    {

        return postService.retrieveAllPosts(pageNo, pageSize, sortBy, sortWay);
    }

    @Operation(
            summary = "Retrieve Post API"
            //description = "Create Category API is used to save Category in Database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "OK"
    )
    @GetMapping("{id}")
    public ResponseEntity<PostDto> retrievePostById(@PathVariable("id") int id){
        return ResponseEntity.ok(postService.retrievePostById(id));
    }

    @Operation(
            summary = "Updates Post API"
            //description = "Create Category API is used to save Category in Database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "OK"
    )
    @SecurityRequirement(
            name = "Jwt Authentication"
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable("id") long id){
        PostDto updatedPost = postService.updatePost(postDto, id);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete comment API"
            //description = "Create Category API is used to save Category in Database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "OK"
    )
    @SecurityRequirement(
            name = "Jwt Authentication"
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") long id){
        postService.deleteById(id);
        return new ResponseEntity<>(String.format("Post with Id : %s deleted successfully",id), HttpStatus.OK);
    }

    @Operation(
            summary = "Retrieve Post by Category API"
            //description = "Create Category API is used to save Category in Database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "OK"
    )
    @GetMapping("/categories/{id}")
    public ResponseEntity<List<PostDto>> retrievePostsByCategoryId(@PathVariable("id") long categoryId){

        return new ResponseEntity<>(postService.retrivePostsbyCategory(categoryId), HttpStatus.OK);
    }
}
