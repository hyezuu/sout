package com.sout.repository;

import com.sout.common.enums.UserStatus;
import com.sout.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@TestPropertySource("classpath:application-local.yml")
@Sql("/sql/user-repository-test-data.sql")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByIdAndStatus_로_유저_데이터를_찾아올_수_있다() {
        //given
        //when
        Optional<User> result = userRepository.findByIdAndStatus(1L, UserStatus.ACTIVE);
        //then
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    void findByIdAndStatus_는_데이터가_없으면_Optional_empty_를_내려준다() {
        //given
        //when
        Optional<User> result = userRepository.findByIdAndStatus(1L, UserStatus.PENDING);
        //then
        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    void findByEmailAndStatus_로_유저_데이터를_찾아올_수_있다() {
        //given
        //when
        Optional<User> result = userRepository.findByEmailAndStatus("admin@admin.com", UserStatus.ACTIVE);
        //then
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    void findByEmailAndStatus_는_데이터가_없으면_Optional_empty_를_내려준다() {
        //given
        //when
        Optional<User> result = userRepository.findByEmailAndStatus("admin@admin.com", UserStatus.PENDING);
        //then
        assertThat(result.isEmpty()).isTrue();
    }
}