package com.my.blog.my.blog.controller;

import com.my.blog.my.blog.payload.PostDTO;
import com.my.blog.my.blog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/posts")
public class PostController {

    private PostService postService;
    public PostController(PostService postService) {

        this.postService = postService;
    }

@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDTO postDto, BindingResult result) {
           if(result.hasErrors()){
               return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
           }

        PostDTO dto = postService.createPost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }


    @GetMapping
    public  List<PostDTO> listAllPosts(@RequestParam(value="pageNo",defaultValue="0",required=false)int pageNo,
                                     @RequestParam(value="pageSize",defaultValue="5",required=false)int pageSize,
                                     @RequestParam(value="sortBy",defaultValue="id",required=false)String sortBy,
                                     @RequestParam(value="sortDir",defaultValue="asc",required=false)String sortDir){
        return postService.listAllPosts(pageNo,pageSize,sortBy,sortDir);
    }

       @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable(name = "id") long id) {
        PostDTO dto = postService.getPostById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody PostDTO postDto, @PathVariable(name = "id") long id) {
        PostDTO postResponse = postService.updatePost(postDto, id);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable(name = "id") long id) {
        postService.deletePostById(id);
        return new ResponseEntity<>("post is deleted",HttpStatus.OK);
    }
}