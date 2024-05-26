package com.example.test_study.medinum.controller;

import com.example.test_study.user.domain.UserStatus;
import com.example.test_study.user.domain.UserUpdate;
import com.example.test_study.user.infrastructure.UserEntity;
import com.example.test_study.user.infrastructure.UserJpaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SqlGroup({
        @Sql(value = "/sql/user-controller-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserJpaRepository userJpaRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void 사용자는_특정_유저의_정보를_받을_수_있다() throws Exception{
        //given
        //when
        //then
        mockMvc.perform(get("/api/v0/users/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.email").value("kok202@naver.com"))
                .andExpect(jsonPath("$.nickname").value("test202"))
                .andExpect(jsonPath("$.address").value("Ulsan"));
    }

    @Test
    void 존재하지_않는_아이디로_유저를_호출하면_404에러가_발생한다() throws Exception{
        //given
        //when
        //then
        mockMvc.perform(get("/api/v0/users/100"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User에서 ID 100를 찾을 수 없습니다."));
    }

    @Test
    void 사용자는_인증_코드로_계정을_활성화_시킬_수_있다() throws Exception {
        // given
        // when
        // then
        mockMvc.perform(
                        get("/api/v0/users/2/verify")
                                .queryParam("certificationCode", "aaaaaaaa-aaaa-aaaa-aaaaaaaa1aaa"))
                .andExpect(status().isFound());
        UserEntity userEntity = userJpaRepository.findById(2L).get();
        assertThat(userEntity.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void 사용자는_인증_코드가_일치하지_않을_경우_권한_없음_에러를_내려준다() throws Exception {
        // given
        // when
        // then
        mockMvc.perform(
                        get("/api/v0/users/2/verify")
                                .queryParam("certificationCode", "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaac"))
                .andExpect(status().isForbidden());
    }

    @Test
    void 사용자는_내_정보를_불러올_때_개인정보인_주소도_갖고_올_수_있다() throws Exception {
        // given
        // when
        // then
        mockMvc.perform(
                        get("/api/v0/users/me")
                                .header("EMAIL", "kok202@naver.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.email").value("kok202@naver.com"))
                .andExpect(jsonPath("$.nickname").value("test202"))
                .andExpect(jsonPath("$.address").value("Ulsan"));
    }

    @Test
    void 사용자는_내_정보를_수정할_수_있다() throws Exception {
        // given
        UserUpdate userUpdate = UserUpdate.builder()
                .nickname("kok202-n")
                .address("Pangyo")
                .build();

        // when
        // then
        mockMvc.perform(
                        put("/api/v0/users/me")
                                .header("ID", 2)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userUpdate)))
                .andExpect(status().isOk());
    }

}