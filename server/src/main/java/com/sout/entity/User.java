package com.sout.entity;

import com.sout.common.base.BaseTimeEntity;
import com.sout.common.enums.UserStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 12)
    private String username;

    @Column(unique = true, nullable = false, length = 30)
    private String email;

    //암호화 되어서 저장되기 때문에 길이제한을 두지 않는다.
    @Column(nullable = false)
    private String password;

    private String profileImageUrl;

    @Column(length = 200)
    private String bio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status = UserStatus.PENDING;
}
