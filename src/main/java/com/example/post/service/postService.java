package com.example.post.service;

import com.example.post.entity.Post;
import com.example.post.repository.PostRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class postService {
    private final PostRepository<Post, Long> postRepository;

    public postService(PostRepository<Post, Long> postRepository) {
        this.postRepository = postRepository;
    }


    public List<Post> getPosts() {
        return postRepository.getAllPosts();
    }

    public Post createPost(Post post) {
        post.setDate(LocalDateTime.now());
        return postRepository.createPost(post);
    }

    public ResponseEntity<Post> getPostById(Long postId) {
        Post post = postRepository.getPostById(postId);
        if (post != null) {
            return ResponseEntity.ok(post);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Post> updatePost(Long postId, Post updatedPost) {
        Post post = postRepository.getPostById(postId);
        if (post != null) {
            if (post.getPassword().equals(updatedPost.getPassword())) {
                post.setTitle(updatedPost.getTitle());
                post.setAuthor(updatedPost.getAuthor());
                post.setContent(updatedPost.getContent());
                if (postRepository.updatePost(post)) {
                    return ResponseEntity.ok(post);
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                }
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<String> deletePost(Long postId, String password) {
        Post post = postRepository.getPostById(postId);
        if (post != null) {
            if (post.getPassword().equals(password)) {
                if (postRepository.deletePost(postId, password)) {
                    return ResponseEntity.ok("게시글이 성공적으로 삭제되었습니다.");
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                }
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
