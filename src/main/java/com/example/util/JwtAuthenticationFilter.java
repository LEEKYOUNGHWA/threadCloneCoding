package com.example.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.signing.key}")
    private String signingKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    	String jwt = request.getHeader("token");
    	
        /*헤더에 값이 없다면 토큰 확인*/
        if (jwt == null) {
            Cookie[] cookies = request.getCookies(); // 모든 쿠키 가져오기
            if (cookies != null) {
                for (Cookie c : cookies) {
                    String name = c.getName(); // 쿠키 이름 가져오기

                    String value = c.getValue(); // 쿠키 값 가져오기
                    if (name.equals("token")) {
                    	jwt = value;
                    }
                }
            }
        }
        
        SecretKey key = Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8));
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody();

        String username = String.valueOf(claims.get("username"));

        GrantedAuthority a = new SimpleGrantedAuthority("user");
        Authentication auth = new UsernamePasswordAuthenticationToken(username, null, List.of(a));
        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
    	String[] excludePath = {"/",
    			"/css/index.css", 
    			"/user/login", 
    			"/user/join",
    			"/login",
    			"/board",
    			"/h2-console",
    			"/h2-console/"};
        String path = request.getRequestURI();
//        return Arrays.stream(excludePath).anyMatch(path::matches);
    	return request.getMethod().equals("GET")||Arrays.stream(excludePath).anyMatch(path::matches);
//    	 return request.getServletPath().equals("/") 
//    			 ||request.getServletPath().equals("/css/index.css")
//    			 ||request.getServletPath().equals("/board")
//    			 ||request.getServletPath().equals("/user/login")
//    			 ||request.getServletPath().equals("/user/join")
//    			 ||request.getServletPath().equals("/login")
//    			 ||request.getServletPath().equals("/logout");
    }
}