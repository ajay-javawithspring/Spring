package com.stone.springbootrestblog.controller;

import com.stone.springbootrestblog.entity.Comment;
import com.stone.springbootrestblog.payload.CommentDto;
import com.stone.springbootrestblog.payload.CommentResponse;
import com.stone.springbootrestblog.service.CommentService;
import com.stone.springbootrestblog.utils.ApplicationConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(
        name = "REST API for Comments"
)
@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Operation(
            summary = "Create comment API"
            //description = "Create Category API is used to save Category in Database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "CREATED"
    )
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable("postId") long postId, @Valid @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }
/*
    @GetMapping("/posts/{postId}/comments")
    public CommentResponse retrieveAllComments(

            @PathVariable("postId") long postId,
            @RequestParam(value = "pageNo", defaultValue = ApplicationConstants.DEFAULT_PAGE_NO, required = false) long pageNo,
            @RequestParam(value = "pageSize", defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE, required = false) long pageSize,
            @RequestParam(value = "sortBy", defaultValue = ApplicationConstants.DEFAULT_SORTBY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = ApplicationConstants.DEFAULT_SORTDIR, required = false) String sortDir

    ){
        return commentService.retrieveAllComments(postId, pageNo, pageSize, sortBy, sortDir);
    }*/

    @Operation(
            summary = "Retrieve comments API"
            //description = "Create Category API is used to save Category in Database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "OK"
    )
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentsByPostId(@PathVariable("postId") int postId){
        return commentService.getCommentsById(postId);
    }

    @Operation(
            summary = "Retrieve comment by API"
            //description = "Create Category API is used to save Category in Database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "OK"
    )
    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> retrieveCommentById(@PathVariable("postId") long postId, @PathVariable("commentId") long commentId){
        return new ResponseEntity<>(commentService.retrieveCommentById(postId, commentId), HttpStatus.OK);
    }

    @Operation(
            summary = "Update comment API"
            //description = "Create Category API is used to save Category in Database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "OK"
    )
    @SecurityRequirement(
            name = "Jwt Authentication"
    )
    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> updateComment(
            @PathVariable("postId") long postId,
            @PathVariable("commentId") long commentId,
            @Valid @RequestBody CommentDto commentDto
    ){

        commentService.updateCommentById(postId, commentId, commentDto);
        return ResponseEntity.ok("Comment updated successfully");

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
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    private ResponseEntity<String> deleteComment(
            @PathVariable("postId") long postId, @PathVariable("commentId") long commentId
         ){
        commentService.deleteCommentById(postId,commentId);

        return ResponseEntity.ok("Comment deleted successfully");
    }


}
