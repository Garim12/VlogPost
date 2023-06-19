package com.example.post.service;

import com.example.post.entity.Post;
import com.example.post.repository.PostRepository;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@ComponentScan
public class postService {
    private final PostRepository postRepository;

    public postService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getPosts() {
        return postRepository.findAllByOrderByDateDesc();
    }

    public Post createPost(Post post) {
        post.setDate(LocalDateTime.now());
        return postRepository.save(post);
    }

    public ResponseEntity<Post> getPostById(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            return ResponseEntity.ok(post);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Post> updatePost(Long postId, Post updatedPost) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            if (post.getPassword().equals(updatedPost.getPassword())) {
                post.setTitle(updatedPost.getTitle());
                post.setAuthor(updatedPost.getAuthor() != null ? updatedPost.getAuthor() : post.getAuthor());
                post.setContent(updatedPost.getContent());
                postRepository.save(post);
                return ResponseEntity.ok(post);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<String> deletePost(Long postId, String password) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            if (post.getPassword().equals(password)) {
                postRepository.delete(post);
                return ResponseEntity.ok("게시글이 성공적으로 삭제되었습니다.");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private String formatDate(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }
}
