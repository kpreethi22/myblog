package com.my.blog.my.blog.service;

import com.my.blog.my.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
    List<CommentDto>getCommentsByPostId(long postId);

    CommentDto getCommentsById(long postId, long commentId);

    CommentDto updatedComment(long postId, long commentId, CommentDto commentRequest);

    void deleteComment(long postId, long commentId);

    List<CommentDto> getAllComments();
}
