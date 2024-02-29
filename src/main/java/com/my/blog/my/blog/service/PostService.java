package com.my.blog.my.blog.service;

import com.my.blog.my.blog.payload.PostDTO;

import java.util.List;

public interface PostService {


     PostDTO getPostById(long id);



    PostDTO createPost(PostDTO postDto);

    PostDTO updatePost(PostDTO postDto, long id);

    void deletePostById(long id);


    List<PostDTO> listAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);


}
