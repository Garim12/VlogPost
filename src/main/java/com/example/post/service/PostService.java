// PostService.java
package com.example.post.service;

import com.example.post.dto.PostDto;
import com.example.post.entity.Post;
import com.example.post.repository.PostRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getPosts() {
        return postRepository.findAllByOrderByDateDesc();
    }

    public Post createPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setAuthor(postDto.getAuthor());
        post.setPassword(postDto.getPassword());
        post.setContent(postDto.getContent());
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

    public ResponseEntity<Post> updatePost(Long postId, PostDto updatedPostDto) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            if (post.getPassword().equals(updatedPostDto.getPassword())) {
                post.setTitle(updatedPostDto.getTitle());
                post.setAuthor(updatedPostDto.getAuthor() != null ? updatedPostDto.getAuthor() : post.getAuthor());
                post.setContent(updatedPostDto.getContent());
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
}
