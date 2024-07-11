package com.sout.common.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Collection;

@Component
public class BeanPropertyUtils {

    /**
     * 소스 객체에서 null이 아닌 속성만을 대상 객체로 복사(할당)한다.
     * Collection 타입의 속성은 복사하지 않는다.
     *
     * @param source 속성을 복사할 소스 객체
     * @param target 속성을 복사받을 대상 객체
     * @return 속성이 복사된 대상 객체, 입력이 유효하지 않은 경우 null
     */
    public <T> T copyNonNullProperties(T source, T target) {
        //원본 객체 또는 목표 객체가 null 이거나, 타입이 다른 경우 null을 반환한다.
        if (source == null || target == null || !source.getClass().equals(target.getClass())) {
            return null;
        }
        //BeanWrapper를 사용하여 객체의 속성에 접근
        final BeanWrapper src = new BeanWrapperImpl(source);
        final BeanWrapper dest = new BeanWrapperImpl(target);

        //소스 객체의 모든 필드를 순회
        for (final Field property : source.getClass().getDeclaredFields()) {
            //현재 필드의 값을 가져옴
            Object sourceProperty = src.getPropertyValue(property.getName());

            if (sourceProperty != null && !(sourceProperty instanceof Collection<?>)) {
                dest.setPropertyValue(property.getName(), sourceProperty);
            }
        }
        return target;
    }
}
