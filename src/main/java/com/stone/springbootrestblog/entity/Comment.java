package com.stone.springbootrestblog.entity;

import com.stone.springbootrestblog.entity.Post;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="comments", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class Comment {

    @Id
    @SequenceGenerator(
            name = "comment_sequence",
            sequenceName = "comment_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "comment_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private long id;

    private String name;

    @Column(nullable = false)
    private String email;

    private String body;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "post_id",
            nullable = false
    )
    private Post post;
}
