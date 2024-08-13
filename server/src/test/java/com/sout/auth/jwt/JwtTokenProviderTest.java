package com.sout.auth.jwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.jsonwebtoken.Claims;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class JwtTokenProviderTest {

    @InjectMocks
    private JwtTokenProvider jwtTokenProvider;

    private static final String SECRET_KEY = "testSecretKeytestSecretKeytestSecretKey";
    private String base64EncodedSecretKey;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(jwtTokenProvider, "secretKey", SECRET_KEY);
        ReflectionTestUtils.setField(jwtTokenProvider, "accessTokenExpirationSeconds", 3600);
        ReflectionTestUtils.setField(jwtTokenProvider, "refreshTokenExpirationSeconds", 86400);
        base64EncodedSecretKey = jwtTokenProvider.encodeBase64SecretKey(SECRET_KEY);
    }

    @Test
    void 유효한_액세스_토큰을_생성_할_수_있다() {
        // Given
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", "testUser");
        String subject = "testSubject";

        // When
        String token = jwtTokenProvider.generateAccessToken(claims, subject, base64EncodedSecretKey);

        // Then
        assertNotNull(token);
        assertTrue(jwtTokenProvider.validateToken(token));
    }

    @Test
    void 유효한_리프레시_토큰을_생성_할_수_있다() {
        // Given
        String subject = "testSubject";

        // When
        String token = jwtTokenProvider.generateRefreshToken(subject, base64EncodedSecretKey);

        // Then
        assertNotNull(token);
        assertTrue(jwtTokenProvider.validateToken(token));
    }

    @Test
    void 유효한_토큰임을_검증_할_수_있다() {
        // Given
        String token = jwtTokenProvider.generateAccessToken(new HashMap<>(), "subject", base64EncodedSecretKey);

        // When
        boolean isValid = jwtTokenProvider.validateToken(token);

        // Then
        assertTrue(isValid);
    }

    @Test
    void 유효하지_않은_토큰임을_검증_할_수_있다() {
        // Given
        String invalidToken = "invalidToken";

        // When
        boolean isValid = jwtTokenProvider.validateToken(invalidToken);

        // Then
        assertFalse(isValid);
    }

    @Test
    void 유효한_리프레시_토큰으로_올바른_주체를_추출할_수_있다() {
        // Given
        String subject = "testSubject";
        String token = jwtTokenProvider.generateRefreshToken(subject, base64EncodedSecretKey);

        // When
        String retrievedSubject = jwtTokenProvider.getSubjectFromRefreshToken(token);

        // Then
        assertEquals(subject, retrievedSubject);
    }

    @Test
    void 유효하지_않은_리프레시_토큰으로_주체_추출시_null_을_반환해야_한다() {
        // Given
        String invalidToken = "invalidToken";

        // When
        String retrievedSubject = jwtTokenProvider.getSubjectFromRefreshToken(invalidToken);

        // Then
        assertNull(retrievedSubject);
    }

    @Test
    void 유효한_토큰으로_클레임을_추출할_수_있다() {
        // Given
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", "testUser");
        String token = jwtTokenProvider.generateAccessToken(claims, "subject", base64EncodedSecretKey);

        // When
        Claims retrievedClaims = jwtTokenProvider.getClaims(token);

        // Then
        assertNotNull(retrievedClaims);
        assertEquals("testUser", retrievedClaims.get("username"));
    }

    @Test
    void 유효하지_않은_토큰으로_클레임_추출시_null을_반환해야_한다() {
        // Given
        String invalidToken = "invalidToken";

        // When
        Claims retrievedClaims = jwtTokenProvider.getClaims(invalidToken);

        // Then
        assertNull(retrievedClaims);
    }
}