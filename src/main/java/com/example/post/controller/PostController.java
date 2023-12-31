// PostController.java
package com.example.post.controller;

import com.example.post.dto.PostDto;
import com.example.post.entity.Post;
import com.example.post.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 전체 게시글 목록 조회 API
    @GetMapping("/posts")
    public List<Post> getAllPosts() {
        return postService.getPosts();
    }

    // 게시글 작성 API
    @PostMapping("/posts")
    public Post createPost(@RequestBody PostDto postDto) {
        return postService.createPost(postDto);
    }

    // 선택한 게시글 조회 API
    @GetMapping("/posts/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }

    // 선택한 게시글 수정 API
    @PutMapping("/posts/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable Long postId, @RequestBody PostDto updatedPostDto) {
        return postService.updatePost(postId, updatedPostDto);
    }

    // 선택한 게시글 삭제 API
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId, @RequestParam String password) {
        return postService.deletePost(postId, password);
    }
}

