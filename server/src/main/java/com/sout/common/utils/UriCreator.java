package com.sout.common.utils;

import com.sout.exception.InvalidUrlException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public final class UriCreator {
    //유틸리티 클래스이므로 인스턴스화 할 수 없다.
    private UriCreator() {}

    public static URI createUri(String defaultUrl, long resourceId) {
        if(defaultUrl==null || defaultUrl.isEmpty()){
            throw new InvalidUrlException("defaultUrl is null or empty");
        }
        return UriComponentsBuilder
                .newInstance()
                .path(defaultUrl + "/{resource-id}")
                .buildAndExpand(resourceId)
                .toUri();
    }
}
