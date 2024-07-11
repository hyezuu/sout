package com.sout.common.utils;

import com.sout.exception.InvalidUrlException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UriCreator 테스트")
class UriCreatorTest {

    @Test
    @DisplayName("유효한 URI를 생성할 수 있다.")
    void createUriShouldCreateValidUri() {
        //given
        String defaultUrl = "/api/resources";
        long resourceId = 1L;

        //when
        URI result = UriCreator.createUri(defaultUrl, resourceId);

        //then
        assertNotNull(result);
        assertEquals("/api/resources/1", result.getPath());
    }

    @Test
    @DisplayName("defaultUrl의 끝에 슬래시가 있는 경우를 처리할 수 있다.")
    void createUriShouldHandleTrailingSlash() {
        //given
        String defaultUrl = "/api/resources/";
        long resourceId = 1L;

        //when
        URI result = UriCreator.createUri(defaultUrl, resourceId);

        //then
        assertNotNull(result);
        assertEquals("/api/resources/1", result.getPath());
    }

    @Test
    @DisplayName("유효하지 않은 입력에 대해 InvalidUrlException을 던질 수 있다.")
    void createUriShouldThrowExceptionForNullDefaultUrl() {
        //given
        String defaultUrl = null;
        long resourceId = 1L;

        //when & then
        assertThrows(InvalidUrlException.class, () -> UriCreator.createUri(defaultUrl, resourceId));
    }

}