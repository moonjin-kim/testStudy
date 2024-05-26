package com.example.test_study.post.infrastructure;

import com.example.test_study.post.domain.Post;
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

    public static PostEntity fromModel(Post post) {
        PostEntity postEntity = new PostEntity();
        postEntity.id = post.getId();
        postEntity.content = post.getContent();
        postEntity.createdAt = post.getCreatedAt();
        postEntity.updateAt = post.getUpdateAt();
        postEntity.writer = UserEntity.fromModel(post.getWriter());
        return postEntity;
    }
    public Post toModel() {
        return Post.builder()
                .id(id)
                .content(content)
                .createdAt(createdAt)
                .updateAt(updateAt)
                .writer(writer.toModel())
                .build();
    }
}
