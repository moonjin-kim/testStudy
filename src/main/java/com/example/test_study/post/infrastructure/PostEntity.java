package com.example.test_study.post.infrastructure;

import com.example.test_study.user.infrastructure.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "post")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="content")
    private String content;

    @Column(name="created_at")
    private Long createdAt;

    @Column(name="update_at")
    private Long updateAt;

    @ManyToOne
    @JoinColumn(name="user_id")
    private UserEntity writer;
}
