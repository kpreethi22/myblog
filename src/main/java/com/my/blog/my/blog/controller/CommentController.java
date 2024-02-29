package com.my.blog.my.blog.controller;

import com.my.blog.my.blog.payload.CommentDto;
import com.my.blog.my.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    //    http://localhost:8080/api/posts/1/comments
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") long postId, @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.createComment(postId,commentDto), HttpStatus.CREATED);
    }
//    http://localhost:8080/api/posts/1/comments
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentsByPostId(@PathVariable(value = "postId") long postId){
        List<CommentDto> comments = commentService.getCommentsByPostId(postId);
        return comments;
    }
    //    http://localhost:8080/api/posts/1/comments/3
    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> getCommentsById(@PathVariable(value = "postId") long postId,@PathVariable(value = "id") long commentId){
        CommentDto commentDto = commentService.getCommentsById(postId,commentId);
        return new ResponseEntity<>(commentDto,HttpStatus.OK);
    }
    //    http://localhost:8080/api/comments
    @GetMapping("/comments")
    public List<CommentDto> getAllComments(){
        return commentService.getAllComments();

    }
    //    http://localhost:8080/api/posts/1/comments/3
    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto>updateComment(@PathVariable(value = "postId") long postId,@PathVariable(value="id") long commentId,@RequestBody CommentDto commentDto){
        CommentDto updatedComment=commentService.updatedComment(postId,commentId,commentDto);
        return new ResponseEntity<>(updatedComment,HttpStatus.OK);
    }
    //    http://localhost:8080/api/posts/1/comments/3
    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String>deleteComment(@PathVariable(value="postId")long postId,@PathVariable(value = "commentId")long commentId){
        commentService.deleteComment(postId,commentId);
        return new ResponseEntity<>("Comment deleted successfully",HttpStatus.OK);
    }
}
