package com.example.post.controller;

import com.example.post.dto.JDBCRepository;
import com.example.post.dto.PostRepository;
import com.example.post.entity.Post;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {
    private final JDBCRepository<Post, Long> postRepository;

    public PostController(JDBCRepository<Post, Long> postRepository) {
        this.postRepository = postRepository;
    }

    // 전체 게시글 목록 조회 API
    @GetMapping("/posts")
    public List<Post> getAllPosts() {
        return postRepository.getAllPosts();
    }

    // 게시글 작성 API
    @PostMapping("/posts")
    public Post createPost(@RequestBody Post post) {
        post.setDate(LocalDateTime.now());
        return postRepository.createPost(post);
    }

    // 선택한 게시글 조회 API
    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable Long postId) {
        Post post = postRepository.getPostById(postId);
        if (post != null) {
            return ResponseEntity.ok(post);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 선택한 게시글 수정 API
    @PutMapping("/posts/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable Long postId, @RequestBody Post updatedPost) {
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

    // 선택한 게시글 삭제 API
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId, @RequestParam String password) {
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

