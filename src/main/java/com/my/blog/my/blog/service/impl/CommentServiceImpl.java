package com.my.blog.my.blog.service.impl;

import com.my.blog.my.blog.entity.Comment;
import com.my.blog.my.blog.entity.Post;
import com.my.blog.my.blog.exception.MyBlogAPIException;
import com.my.blog.my.blog.exception.ResourceNotFoundException;
import com.my.blog.my.blog.payload.CommentDto;
import com.my.blog.my.blog.repository.CommentRepository;
import com.my.blog.my.blog.repository.PostRepository;
import com.my.blog.my.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepo;
   private PostRepository postRepo;

  @Autowired
  private ModelMapper mapper;

    public CommentServiceImpl(PostRepository postRepo) {

        this.postRepo = postRepo;
    }


    @Override

    public CommentDto createComment(long postId, CommentDto commentDto) {
     Comment comment= mapToEntity(commentDto);
     Post post=postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",postId));
     comment.setPost(post);
     Comment newComment=commentRepo.save(comment);
        CommentDto dto = mapToDto(newComment);
        return dto;
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {

        Post post=postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("post not found with id:"+postId));
       List<Comment> comments=commentRepo.findByPostId(postId);
        if(comments!=null){
            return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
              }
            else{
            throw new MyBlogAPIException(HttpStatus.BAD_REQUEST,"comment not found");
        }

    }

    @Override
    public CommentDto getCommentsById(long postId, long commentId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("no Post with id:"+postId));
        Comment comment = commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException(" no Comment with id:"+commentId));
        if(!comment.getPost().getId().equals(post.getId())){
            throw new MyBlogAPIException(HttpStatus.BAD_REQUEST,"comment not founD");
        }
        return mapToDto(comment);
    }

    @Override
    public CommentDto updatedComment(long postId, long commentId, CommentDto commentRequest) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("no Post with id:"+postId));
        Comment comment = commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException(" no Comment with id:"+commentId));
        if(!comment.getPost().getId().equals(post.getId())){
            throw new MyBlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post" );
        }
        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setBody(commentRequest.getBody());
        Comment updatedComment = commentRepo.save(comment);
        return mapToDto((updatedComment));
    }

    @Override
    public void deleteComment(long postId, long commentId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("no Post with id:"+postId));
        Comment comment = commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException(" no Comment with id:"+commentId));
        if(!comment.getPost().getId().equals(post.getId())){
            throw new MyBlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post" );
        }
        commentRepo.deleteById(commentId);
    }

    @Override
    public List<CommentDto> getAllComments() {
        List<Comment> comments = commentRepo.findAll();
        List<CommentDto> commentDtos = comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
        return commentDtos;
    }

    private CommentDto mapToDto(Comment comment) {
        CommentDto commentDto1=mapper.map(comment,CommentDto.class);
//        CommentDto commentDto=new CommentDto();
//        commentDto.setId(newComment.getId());
//        commentDto.setName(newComment.getName());
//        commentDto.setEmail(newComment.getEmail());
//        commentDto.setBody(newComment.getBody());
        return commentDto1;

    }

    private Comment mapToEntity(CommentDto commentDto) {
        Comment comment=mapper.map(commentDto, Comment.class);
//        Comment comment=new Comment();
//        comment.setId(commentDto.getId());
//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());
//        comment.setBody(commentDto.getBody());
        return comment;
    }
}
