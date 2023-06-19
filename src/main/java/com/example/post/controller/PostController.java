package com.example.post.controller;

import com.example.post.entity.Post;
import com.example.post.service.postService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    private final postService postService;

    public PostController(postService postService) {
        this.postService = postService;
    }

    // 전체 게시글 목록 조회 API
    @GetMapping("/posts")
    public List<Post> getAllPosts() {
        return postService.getPosts();
    }

    // 게시글 작성 API
    @PostMapping("/posts")
    public Post createPost(@RequestBody Post post) {
        return postService.createPost(post);
    }

    // 선택한 게시글 조회 API
    @GetMapping("/posts/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }

    // 선택한 게시글 수정 API
    @PutMapping("/posts/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable Long postId,@RequestBody Post updatedPost) {
        return postService.updatePost(postId,updatedPost);
    }

    // 선택한 게시글 삭제 API
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId, @RequestParam String password) {
        return postService.deletePost(postId, password);
    }
}
