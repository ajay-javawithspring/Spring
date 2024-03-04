package com.stone.springbootrestblog.payload;

import com.stone.springbootrestblog.entity.Comment;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentDto {

    private long id;

    @NotEmpty
    @Size(min = 5)
    private String name;

    @Email
    private String email;

    @Size(min = 5, message = "content should be at least five characters")
    private String body;
}
