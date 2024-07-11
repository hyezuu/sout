package com.sout.common.enums;

import lombok.Getter;

@Getter
public enum UserStatus {
    PENDING("인증 대기"),
    ACTIVE("활동 상태"),
    DORMANT("활동 상태"),
    WITHDRAWN("탈퇴 상태");

    private final String status;

    UserStatus(String status) {
        this.status = status;
    }
}
