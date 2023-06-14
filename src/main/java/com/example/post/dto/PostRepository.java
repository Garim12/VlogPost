package com.example.post.dto;

import com.example.post.entity.Post;

import java.util.List;

public interface PostRepository {
    List<Post> findAllByOrderByDateDesc();
    Post createPost(Post post);
    Post getPostById(Long postId);
    boolean updatePost(Post updatedPost);
    boolean deletePost(Long postId, String password);
}

