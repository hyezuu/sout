package com.sout.entity;

import com.sout.common.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "users")
@Getter @Setter
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    //암호화 되어서 저장되기 때문입니다 ~~!
    @Column(nullable = false)
    private String password;

    private String profileImageUrl;

    private String bio;

    private UserStatus status



    private
}
