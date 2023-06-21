// PostDto.java
package com.example.post.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDto {
    private Long id;
    private String title;
    private String author;
    private String password;
    private String content;
}
