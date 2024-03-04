package com.stone.springbootrestblog.service.impl;

import com.stone.springbootrestblog.entity.Comment;
import com.stone.springbootrestblog.entity.Post;
import com.stone.springbootrestblog.exception.BlogApiException;
import com.stone.springbootrestblog.exception.ResourceNotFoundException;
import com.stone.springbootrestblog.payload.CommentDto;
import com.stone.springbootrestblog.payload.CommentResponse;
import com.stone.springbootrestblog.repository.CommentRepository;
import com.stone.springbootrestblog.repository.PostRepository;
import com.stone.springbootrestblog.service.CommentService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Transactional
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;


    private Post getPost(long postId) {
        return postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("post", "id", postId));
    }

    private Comment getComment(long commentId){
        return commentRepository.findById(commentId).orElseThrow(
                ()->new ResourceNotFoundException("Comment", "Id", commentId));
    }

    private void throwCommentNotFoundExceptionIfExists(Post post, Comment comment) {
        if (!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to the Post");
        }
    }
    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {

        Comment comment = mapToEntity(commentDto);
        Post post = getPost(postId);
        comment.setPost(post);

        Comment newComment = commentRepository.save(comment);
        return mapToDto(newComment);
    }

    @Override
    public CommentDto retrieveCommentById(long postId, long commentId) {
        Post post = getPost(postId);
        Comment comment = getComment(commentId);

        throwCommentNotFoundExceptionIfExists(post, comment);
        return mapToDto(comment);
    }

    @Override
    public void updateCommentById(long postId, long commentId, CommentDto commentDto) {
        Post post = getPost(postId);
        Comment comment = getComment(commentId);
        throwCommentNotFoundExceptionIfExists(post, comment);

        if(!commentDto.getEmail().equals(comment.getEmail())){
            throw new BlogApiException( HttpStatus.BAD_REQUEST, "User does not not have made this comment");
        }

        comment.setName(commentDto.getName());
        comment.setBody(commentDto.getBody());
        //comment.setEmail(commentDto.getEmail());

        commentRepository.save(comment);
    }

    @Override
    public void deleteCommentById(long postId, long commentId) {
        Post post = getPost(postId);
        Comment comment = getComment(commentId);
        throwCommentNotFoundExceptionIfExists(post, comment);
        commentRepository.deleteById(commentId);
    }

//    @Override
//    public CommentResponse retrieveAllComments(
//            long postId, long pageNo, long pageSize, String sortBy, String sortDir
//    ) {
//
//        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
//                :Sort.by(sortBy).descending();
//
//        Pageable pageable = PageRequest.of( (int) pageNo, (int) pageSize, sort );
//        Page<Comment> commentPage = commentRepository.findAll(pageable);
//
//        List<Comment> commentList = commentPage.getContent();
//        List<CommentDto> responseCommentList = commentList.stream().map(this::mapToDto).collect(Collectors.toList());
//
//        CommentResponse commentResponse = new CommentResponse();
//
//        commentResponse.setCommentsList(responseCommentList);
//        commentResponse.setPageNo(commentPage.getNumber());
//        commentResponse.setPageSize(commentPage.getSize());
//        commentResponse.setNoOfComments(commentPage.getTotalElements());
//        commentResponse.setNoOfPages(commentPage.getTotalPages());
//        commentResponse.setLast(commentPage.isLast());
//
//        return commentResponse;
//    }

    @Override
    public List<CommentDto> getCommentsById(int postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);

        return comments.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private CommentDto mapToDto(Comment comment){

        //commentDto.setId(comment.getId());
//        commentDto.setBody(comment.getBody());
//        commentDto.setName(comment.getName());
//        commentDto.setEmail(comment.getEmail());

        return modelMapper.map(comment, CommentDto.class);
    }

    private Comment mapToEntity(CommentDto commentDto){

        return modelMapper.map(commentDto, Comment.class);
    }
}
