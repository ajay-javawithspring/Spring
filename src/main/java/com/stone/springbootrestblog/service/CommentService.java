package com.stone.springbootrestblog.service;

import com.stone.springbootrestblog.entity.Post;
import com.stone.springbootrestblog.payload.CommentDto;
import com.stone.springbootrestblog.payload.CommentResponse;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);

    CommentDto retrieveCommentById(long postId, long commentId);

    void updateCommentById(long postId, long commentId, CommentDto commentDto);

    void deleteCommentById(long postId, long commentId);

    //CommentResponse retrieveAllComments(long postId, long pageNo, long pageSize, String sortBy, String sortDir );

    List<CommentDto> getCommentsById(int postId);
}
