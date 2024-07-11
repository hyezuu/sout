package com.sout.common.utils;

import com.sout.common.utils.testmodel.TestBean;
import com.sout.common.utils.testmodel.TestBeanWithList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BeanPropertyUtilsTest {

    private BeanPropertyUtils beanPropertyUtils;

    @BeforeEach
    void setUp() {
        beanPropertyUtils = new BeanPropertyUtils();
    }

    @Test
    @DisplayName("null이 아닌 속성만 복사되어야 한다")
    void copyNonNullProperties_ShouldCopyOnlyNonNullProperties() {
        TestBean source = new TestBean("John", 30, null);
        TestBean target = new TestBean("James", 30, "Old note");

        TestBean result = beanPropertyUtils.copyNonNullProperties(source, target);

        assertNotNull(result);
        assertEquals("John", result.getName());
        assertEquals(30, result.getAge());
        assertEquals("Old note", result.getNote());
    }

    @Test
    @DisplayName("Collection 타입의 속성은 복사되지 않아야 한다")
    void copyNonNullProperties_ShouldNotCopyCollectionProperties() {
        TestBeanWithList source = new TestBeanWithList("John", new ArrayList<>());
        TestBeanWithList target = new TestBeanWithList(null, null);

        source.getItems().add("Item1");

        TestBeanWithList result = beanPropertyUtils.copyNonNullProperties(source, target);

        assertNotNull(result);
        assertEquals("John", result.getName());
        assertNull(result.getItems());
    }

    @Test
    @DisplayName("소스가 null이면 null을 반환해야 한다")
    void copyNonNullProperties_ShouldReturnNull_WhenSourceIsNull() {
        TestBean target = new TestBean();
        TestBean result = beanPropertyUtils.copyNonNullProperties(null, target);
        assertNull(result);
    }

    @Test
    @DisplayName("타겟이 null이면 null을 반환해야 한다")
    void copyNonNullProperties_ShouldReturnNull_WhenTargetIsNull() {
        TestBean source = new TestBean();
        TestBean result = beanPropertyUtils.copyNonNullProperties(source, null);
        assertNull(result);
    }

    @Test
    @DisplayName("소스와 타겟의 클래스가 다르면 null을 반환해야 한다")
    void copyNonNullProperties_ShouldReturnNull_WhenClassesDiffer() {
        TestBean source = new TestBean();
        TestBeanWithList target = new TestBeanWithList();
        TestBeanWithList result = (TestBeanWithList) beanPropertyUtils.copyNonNullProperties(source, target);
        assertNull(result);
    }
}