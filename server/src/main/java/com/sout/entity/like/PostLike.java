package com.sout.entity.like;

import com.sout.common.base.BaseCreatedTimeEntity;
import com.sout.entity.Post;
import com.sout.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "post_likes")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "post_id"}))
public class PostLike extends BaseCreatedTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
}