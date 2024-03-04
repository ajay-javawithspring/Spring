package com.stone.springbootrestblog.payload;

import com.stone.springbootrestblog.entity.Category;
import com.stone.springbootrestblog.entity.Comment;
import com.stone.springbootrestblog.entity.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        description = "Post Model Information"
)
@Configuration
public class PostDto {

    private Long id;
    @Schema(
            description = "Title Information"
    )
    @NotEmpty
    @Size(min = 5, message = "Title should at least five characters long")
    private String title;
    @Schema(
            description = "Post Description"
    )
    private String description;
    @Schema(
            description = "Post content Information"
    )
    private String content;
    @Schema(
            description = "Post Category"
    )
    private Long categoryId;
    @Schema(
            description = "Post comments"
    )
    public Set<CommentDto> comments;

}
