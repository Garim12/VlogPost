package com.example.post.dto;

import com.example.post.entity.Post;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class JDBCRepository<P, L extends Number> {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/post";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "9961";

    // 게시글 목록 조회
    public List<Post> getAllPosts() {
        List<Post> posts = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement()) {
            String query = "SELECT * FROM post ORDER BY date DESC";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Post post = new Post();
                post.setId(rs.getLong("id"));
                post.setTitle(rs.getString("title"));
                post.setAuthor(rs.getString("author"));
                post.setPassword(rs.getString("password"));
                post.setContent(rs.getString("content"));
                post.setDate(rs.getTimestamp("date").toLocalDateTime());
                posts.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return posts;
    }

    // 게시글 작성
    public Post createPost(Post post) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO post (title, author, password, content, date) VALUES (?, ?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, post.getTitle());
            stmt.setString(2, post.getAuthor());
            stmt.setString(3, post.getPassword());
            stmt.setString(4, post.getContent());
            stmt.setTimestamp(5, Timestamp.valueOf(post.getDate()));

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating post failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    post.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating post failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return post;
    }

    // 선택한 게시글 조회
    public Post getPostById(Long postId) {
        Post post = null;

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM post WHERE id = ?")) {
            stmt.setLong(1, postId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                post = new Post();
                post.setId(rs.getLong("id"));
                post.setTitle(rs.getString("title"));
                post.setAuthor(rs.getString("author"));
                post.setPassword(rs.getString("password"));
                post.setContent(rs.getString("content"));
                post.setDate(rs.getTimestamp("date").toLocalDateTime());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return post;
    }

    // 선택한 게시글 수정
    public boolean updatePost(Post updatedPost) {
        boolean success = false;

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("UPDATE post SET title = ?, content = ? WHERE id = ? AND password = ?")) {
            stmt.setString(1, updatedPost.getTitle());
            stmt.setString(2, updatedPost.getContent());
            stmt.setLong(3, updatedPost.getId());
            stmt.setString(4, updatedPost.getPassword());

            int affectedRows = stmt.executeUpdate();

            success = (affectedRows > 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    // 선택한 게시글 삭제
    public boolean deletePost(Long postId, String password) {
        boolean success = false;

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM post WHERE id = ? AND password = ?")) {
            stmt.setLong(1, postId);
            stmt.setString(2, password);

            int affectedRows = stmt.executeUpdate();

            success = (affectedRows > 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }
}
