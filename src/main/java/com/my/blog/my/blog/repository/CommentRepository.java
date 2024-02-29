package com.my.blog.my.blog.repository;


import com.my.blog.my.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
   List<Comment> findByPostId(long postId);
}
