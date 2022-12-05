package com.sparta.blog.jwt;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {
    //Header Key값
    public static final String AUTHORIZATION_HEADER = "Authorization";

    /*public static final String AUTHORIZATION_KEY = "auth";*/
    //위의 코드는 사용자 권한을 설정하는데 사용하는 코드 과제에서는 필요가 없다.

    //Token 식별자
    private static final String BEARER_PREFIX = "Bearer ";

    //토큰 만료 시간을 의미. 밀리세컨드 기준이다. 60 * 1000L은 60초. 60초 * 60 은 1시간.
    private static final long TOKEN_TIME = 60 * 60 * 1000L;

    //@Value를 사용하면 application.properties에 추가해둔 JWT 키 값을 가져올 수 있다.
    @Value("${jwt.secret.key}")
    private String secretKey;

    //Key 객체는 우리가 Token을 만들 때 넣어줄 Key값, secretKey가 여기에 들어간다고 생각하면 된다.
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    //@PostConstruct는 이 객체가 생성이 될 때 초기화하는 함수이다.
    //base64로 인코딩이 되어있기 때문에 값을 가져와서 한번 decode하는 것. 반환값이 byte라 byte배열로 받은 후
    //key객체에 넣어준다. key객체에 넣을 때는 hmacShaKeyFor() 메서드를 사용해서 메서드 인자로 byte배열을 준다.
    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // header 토큰을 가져오기
    //HttpServletRequest 객체 안에는 우리가 가져와야 할 토큰이 헤더에 들어와 있다. 그래서 request.getHeader를 사용해서 가져온다.
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            //substring을 하는 이유는 BEARER로 시작하기 때문에 앞에 6자에 공백 한 칸을 지워준다.
            //BEARER은 TOKEN과 아무런 상관이 없는 글자이다.
            return bearerToken.substring(7);
        }
        return null;
    }

    // 토큰 생성
    public String createToken(String username) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username)
                        /*.claim(AUTHORIZATION_KEY, role)*/ //이거 권한
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME)) //이 시점 기준으로 1시간동안 유효
                        .setIssuedAt(date)  //토큰 생성 시간.
                        .signWith(key, signatureAlgorithm)  //실제로 만든 키 객체와 그 키 객체를 어떤 알고리즘으로 암호화할것인지 정해줌
                        .compact();
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    // 토큰에서 사용자 정보 가져오기
    //getBody로 안에 있는 내용 가져오기.
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

}