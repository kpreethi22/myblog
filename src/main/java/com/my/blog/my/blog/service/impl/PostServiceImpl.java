package com.my.blog.my.blog.service.impl;

import com.my.blog.my.blog.entity.Post;
import com.my.blog.my.blog.exception.ResourceNotFoundException;
import com.my.blog.my.blog.payload.PostDTO;
import com.my.blog.my.blog.repository.PostRepository;
import com.my.blog.my.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepo;
    private ModelMapper mapper;

    public PostServiceImpl(PostRepository postRepo, ModelMapper mapper) {
        this.postRepo = postRepo;
        this.mapper = mapper;
    }

    public PostServiceImpl(PostRepository postRepo) {
        this.postRepo = postRepo;
        this.mapper = new ModelMapper();
    }

    public PostServiceImpl(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public PostServiceImpl() {
        this.mapper = new ModelMapper();
    }

    @Override
    public PostDTO createPost(PostDTO postDto) {
      Post post=mapToEntity(postDto);

//     Post post =new Post();
//       post.setTitle(postDto.getTitle());
//       post.setDescription(postDto.getDescription());
//       post.setContent(postDto.getContent());
       Post newPost=postRepo.save(post);
        PostDTO dto=mapToDto(newPost);
//       PostDTO dto =new PostDTO();
//       dto.setId(newPost.getId());
//       dto.setTitle(newPost.getTitle());
//       dto.setDescription(newPost.getDescription());
//       dto.setContent(newPost.getContent());
        return dto;
    }
    @Override
    public List<PostDTO> listAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable= PageRequest.of(pageNo,pageSize,sort);
        Page<Post> posts=postRepo.findAll(pageable);
        List<Post>listOfPosts=posts.getContent();
        List<PostDTO>postDtos=listOfPosts.stream().map(post->mapToDto(post)).collect(Collectors.toList());
        return postDtos;
    }
    @Override
    public PostDTO getPostById(long id) {
        Post newPost = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("resource not found:" + id));
        PostDTO postDto=mapToDto(newPost);
        return postDto;
    }
    @Override
    public PostDTO updatePost(PostDTO postDto, long id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("resource not found:" + id));
        Post newPost= mapToEntity(postDto);
       newPost.setId(id);
       Post updatedPost =postRepo.save(newPost);
       PostDTO dto= mapToDto(updatedPost);
       return dto;
    }
    @Override
    public void deletePostById(long id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("resource not found:" + id));
        postRepo.deleteById(id);
    }
    private PostDTO mapToDto(Post post) {
       PostDTO postdto =mapper.map(post,PostDTO.class);
//        PostDTO dto =new PostDTO();
//        dto.setId(post.getId());
//        dto.setTitle(post.getTitle());
//        dto.setDescription(post.getDescription());
//        dto.setContent(post.getContent());
        return postdto;
    }
    private Post mapToEntity(PostDTO postDto) {
        Post post=mapper.map(postDto,Post.class);
//        Post post=new Post();
//        post.setId(postDto.getId());
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post;
    }
}
